package com.gh.rxretrofitlibrary.http.cookie;

import com.gh.rxretrofitlibrary.utils.CookieDbUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author: gh
 * @description: gson持久化截取保存数据
 * @date: 2017/2/28 09:34
 * @note:
 */

public class CookieInterceptor implements Interceptor{

    private CookieDbUtil dbUtil;
    /**
     * 是否缓存标识
     */
    private boolean cache;
    /**
     * url
     */
    private String url;

    public CookieInterceptor(boolean cache, String url) {
        dbUtil = CookieDbUtil.getInstance();
        this.cache = cache;
        this.url = url;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (cache) {
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            //字符集
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            //返回数据
            String bodyString = buffer.clone().readString(charset);
            CookieResulte resulte = dbUtil.queryCookieBy(url);
            long time = System.currentTimeMillis();
            if (resulte == null) {
                resulte = new CookieResulte(url, bodyString, time);
                dbUtil.saveCookie(resulte);
            } else {
                resulte.setResulte(bodyString);
                resulte.setTime(time);
                dbUtil.updateCookie(resulte);
            }
        }
        return response;
    }
}
