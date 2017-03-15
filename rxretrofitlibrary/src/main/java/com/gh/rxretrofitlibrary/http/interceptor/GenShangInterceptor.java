package com.gh.rxretrofitlibrary.http.interceptor;

import com.gh.rxretrofitlibrary.utils.SignUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: gh
 * @description: 跟上项目的插值器,由于项目已进行很长时间,兼容以前的接口,对url进行重新拼装
 * @date: 2017/2/28 18:22
 * @note: 可以借鉴
 */

public class GenShangInterceptor implements Interceptor {

    public static final String CLIENT_SECRET_VALUE = "F85924D5-2045-4A7D-9174-B97059C810EC";//应用的密钥

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Map<String, Object> map = new HashMap();
        //得到？后面的部分
        String url_param = request.url().url().toString().split("[?]")[1];
        //把每个参数分开
        String[] params = url_param.split("&");
        //分开键和值
        for (String param : params) {
            String[] keyvalue = param.split("=");
            if (keyvalue.length == 1) {
                String key = keyvalue[0];
                keyvalue = new String[]{key, ""};
            }
            if (keyvalue[1] != null)
                keyvalue[1] = URLDecoder.decode(keyvalue[1], "UTF-8");
            if (!"sign".equals(keyvalue[0]))
                map.put(keyvalue[0], keyvalue[1]);
        }
        //添加新参数
//        map.put("version", "11");
        //签名
        String sign = null;
        try {
            sign = SignUtil.sign(map, CLIENT_SECRET_VALUE);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        //重新定义url
        //拼参数
        List<String> paramNames = new ArrayList(map.size());
        paramNames.addAll(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (String paramName : paramNames) {
            sb.append(paramName).append("=").append(map.get(paramName) == null ? "" : URLEncoder.encode((String) map.get(paramName), "UTF-8")).append("&");
        }
        //拼签名
        sb.append("sign=" ).append(sign);
        //最终的url
        String newUrl = request.url().url().toString().split("[?]")[0]+"?"+sb.toString();

        Request.Builder builder = request.newBuilder()
                .url(newUrl);
        Request request2 = builder.build();
        return chain.proceed(request2);
    }

}
