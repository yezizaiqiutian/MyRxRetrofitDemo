package com.gh.myrxretrofitdemo.entity.api;

import com.gh.myrxretrofitdemo.HttpPostService;
import com.gh.rxretrofitlibrary.api.BaseApi;
import com.gh.rxretrofitlibrary.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author: gh
 * @description: 测试跟上数据     BaseResultEntity中data必须改成对应的参数名....所以很麻烦
 * @date: 2017/2/28 15:19
 * @note:
 */

public class GenShangTripApi extends BaseApi{

    //http://test.igenshang.com:8001/openapi/api/
    // schedule/getLeaderScheduleList
    // ?access_token=174e7468-4dd4-4f52-9683-c2f4af6cf956
    // &timestamp=1488266100162
    // &ver=1.0
    // &client_id=BG9CJ9WRB
    // &sign=E8457210128CE8E9BF0CFCE4352E3EBE


    public GenShangTripApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
        setCancel(true);
        setCache(false);
        setBaseUrl("http://test.igenshang.com:8001/openapi/");
        setMethod("api/schedule/getLeaderScheduleList");
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService service = retrofit.create(HttpPostService.class);
        return service.getLeaderScheduleList(
                "174e7468-4dd4-4f52-9683-c2f4af6cf956",
                "1.0",
                "BG9CJ9WRB",
                "1488266100162",
                "E8457210128CE8E9BF0CFCE4352E3EBE");
    }
}
