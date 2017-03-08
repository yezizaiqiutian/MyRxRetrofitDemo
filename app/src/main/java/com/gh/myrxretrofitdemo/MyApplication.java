package com.gh.myrxretrofitdemo;

import android.app.Application;
import android.content.Context;

import com.gh.rxretrofitlibrary.RxRetrofitApp;

/**
 * @author: gh
 * @description: Application
 * @date: 2017/2/28 15:00
 * @note:
 */

public class MyApplication extends Application{

    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        RxRetrofitApp.init(this);
    }
}
