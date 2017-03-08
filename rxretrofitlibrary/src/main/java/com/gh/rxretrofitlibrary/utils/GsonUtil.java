package com.gh.rxretrofitlibrary.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @author: gh
 * @description: TODO(描述)
 * @date: 2017/3/2 11:24
 * @note:
 */

public class GsonUtil {

    public static <T> T gson2Entity(Class<T> t,String s) {
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        T resultEntity= gson.fromJson(s, type);
        return resultEntity;
    }

}
