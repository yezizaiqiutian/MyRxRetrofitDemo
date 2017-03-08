package com.gh.rxretrofitlibrary;

import android.app.Application;

/**
 * @author: gh
 * @description: 全局Application
 * @date: 2017/2/27 17:00
 * @note:
 */

public class RxRetrofitApp {

    private static Application application;

    public static void init(Application application) {
        setApplication(application);
    }

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }
}
