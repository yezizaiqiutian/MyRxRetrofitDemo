package com.gh.rxretrofitlibrary.api;

import com.gh.rxretrofitlibrary.exception.HttpTimeException;
import com.gh.rxretrofitlibrary.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author: gh
 * @description: 请求数据统一封装类
 * @date: 2017/2/27 17:02
 * @note:
 */

public abstract class BaseApi<T> implements Func1<BaseResultEntity<T>,T>{

    /**
     * rx生命周期管理
     */
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /**
     * 回调
     */
    private SoftReference<HttpOnNextListener> listener;
    /**
     * 是否能取消加载框
     */
    private boolean cancel;
    /**
     * 是否显示加载框
     */
    private boolean showProgress;
    /**
     * 是否需要缓存处理
     */
    private boolean cache;
    /**
     * baseUrl
     */
    private String baseUrl = "http://www.izaodao.com/Api/";
    /**
     * 方法   如果需要缓存必须设置这个参数;不需要不用设置
     */
    private String method;
    /**
     * 超时时间 默认6秒
     */
    private int connectionTime = 6;
    /**
     * 有网情况下的本体缓存默认时间   默认60秒
     */
    private int cookieNetWorkTime = 60;
    /**
     * 无网情况下的本体缓存默认时间   默认30天
     */
    private int cookieNoNetWorkTime = 30*24*60*60;
    /**
     * retry次数
     */
    private int retryCount = 3;
    /**
     * 延迟
     */
    private long retryDelay = 3000;
    /**
     * 叠加延迟
     */
    private long retryIncreaseDelay = 3000;

    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
        setCache(true);
    }

    public abstract Observable getObservable(Retrofit retrofit);

    @Override
    public T call(BaseResultEntity<T> httpResult) {
        // TODO: 2017/2/28 这里需要改成不等于正确code
        if (httpResult.getRet() == 0) {
            throw new HttpTimeException(httpResult.getRet());
        }
        return httpResult.getData();
    }

    public String getUrl() {
        return baseUrl + method;
    }


    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference<RxAppCompatActivity>(rxAppCompatActivity);
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference<HttpOnNextListener>(listener);
    }

    /*****************************************GET&SET********************************/

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }
}
