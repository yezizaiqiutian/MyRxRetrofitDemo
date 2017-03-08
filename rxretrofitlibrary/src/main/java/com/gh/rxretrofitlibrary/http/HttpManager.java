package com.gh.rxretrofitlibrary.http;

import com.gh.rxretrofitlibrary.api.BaseApi;
import com.gh.rxretrofitlibrary.exception.RetryWhenNetworkException;
import com.gh.rxretrofitlibrary.http.cookie.CookieInterceptor;
import com.gh.rxretrofitlibrary.http.interceptor.CommonInterceptor;
import com.gh.rxretrofitlibrary.subscribers.ProgressSubscriber;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: gh
 * @description: http交互处理类
 * @date: 2017/2/27 18:10
 * @note:
 */

public class HttpManager {

    private volatile static HttpManager INSTANCE;

    private HttpManager() {

    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    public void doHttpDeal(BaseApi baseApi) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(baseApi.getConnectionTime(), TimeUnit.SECONDS);
        builder.addInterceptor(new CommonInterceptor());
        if (baseApi.isCache()) {
            // TODO: 2017/2/27 已经判断是否缓存是否还需要传?
            builder.addInterceptor(new CookieInterceptor(baseApi.isCache(), baseApi.getUrl()));
        }

        //创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseApi.getBaseUrl())
                .build();

        //rx处理
        ProgressSubscriber subscriber = new ProgressSubscriber(baseApi);
        Observable observable = baseApi.getObservable(retrofit)
                .retryWhen(new RetryWhenNetworkException(baseApi.getRetryCount(),baseApi.getRetryDelay(),baseApi.getRetryIncreaseDelay()))
                .compose(baseApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(baseApi);

        observable.subscribe(subscriber);
    }

}
