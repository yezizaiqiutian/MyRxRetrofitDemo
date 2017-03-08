package com.gh.rxretrofitlibrary.http.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: gh
 * @description: 封装公共参数（Key和密码）
 * @date: 2017/2/28 17:53
 * @note:http://blog.csdn.net/jdsjlzx/article/details/52063950
 */

public class CommonInterceptor implements Interceptor {

    //示例参数
    private  String key1 = "key1";
    private  String value1 = "value1";
    private  String key2 = "key2";
    private  String value2 = "value2";

    public CommonInterceptor() {
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();

        //可供使用的旧Url
        String oldUrl = oldRequest.url().url().toString();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(key1, value1)
                .addQueryParameter(key2, value2);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
