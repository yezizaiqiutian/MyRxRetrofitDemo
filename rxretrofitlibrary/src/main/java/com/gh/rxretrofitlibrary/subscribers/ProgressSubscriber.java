package com.gh.rxretrofitlibrary.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.gh.rxretrofitlibrary.RxRetrofitApp;
import com.gh.rxretrofitlibrary.api.BaseApi;
import com.gh.rxretrofitlibrary.api.BaseResultEntity;
import com.gh.rxretrofitlibrary.exception.HttpTimeException;
import com.gh.rxretrofitlibrary.http.cookie.CookieResulte;
import com.gh.rxretrofitlibrary.listener.HttpOnNextListener;
import com.gh.rxretrofitlibrary.utils.AppUtil;
import com.gh.rxretrofitlibrary.utils.CookieDbUtil;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;

/**
 * @author: gh
 * @description: 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * @date: 2017/2/27 18:18
 * @note:
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    /**
     * 是否弹框
     */
    private boolean showProgress = true;
    /**
     * 软引用回调接口
     */
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    /**
     * 软引用Activity
     */
    private SoftReference<RxAppCompatActivity> mActivity;
    /**
     * 加载框  可自定义
     */
    private ProgressDialog pd;
    /**
     * 请求数据
     */
    private BaseApi api;

    public ProgressSubscriber(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowProgress(api.isShowProgress());
        if (isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }

    /**
     * 订阅开始时调用
     */
    @Override
    public void onStart() {
        //显示ProgressDialog
        showProgressDialog();
        //满足条件  读取缓存
        if (api.isCache() && AppUtil.isNetworkAvavailable(RxRetrofitApp.getApplication())) {
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null && cookieResulte.getResulte() != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                    if (mSubscriberOnNextListener.get() != null) {
//                        mSubscriberOnNextListener.get().onCacheNext(gson2Entity(cookieResulte.getResulte()), "有网-时间小于间隔");
                        mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte(), "有网-时间小于间隔");
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }

    /**
     * 完成   隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 失败时尝试读取缓存
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (api.isCache()) {
            Observable.just(api.getUrl())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            errorDo(e);
                        }

                        @Override
                        public void onNext(String s) {
                            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                            if (cookieResulte == null) {
                                throw new HttpTimeException("网络错误");
                            }
                            long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                            if (time < api.getCookieNoNetWorkTime()) {
                                if (mSubscriberOnNextListener != null) {
//                                    mSubscriberOnNextListener.get().onCacheNext(gson2Entity(cookieResulte.getResulte()), "有网-时间小于间隔");
                                    mSubscriberOnNextListener.get().onCacheNext(cookieResulte.getResulte(), "有网-时间小于间隔");
                                }
                            } else {
                                CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                                throw new HttpTimeException("网络错误");
                            }
                        }
                    });
        } else {
            errorDo(e);
        }

    }

    /**
     * 对错误统一处理
     *
     * @param e
     */
    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpTimeException) {
            Toast.makeText(context, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
        }
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
    }

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }
    }

    /**
     * 初始化加载框
     *
     * @param cancel
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mSubscriberOnNextListener.get() != null) {
                            mSubscriberOnNextListener.get().onCancel();
                        }
                        onCancelProgress();
                    }
                });
            }
        }
    }

    private T gson2Entity(String cache) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseResultEntity<T>>() {
        }.getType();
        BaseResultEntity<T> resultEntity = gson.fromJson(cache, type);
//        BaseResultEntity<T> resultEntity = gson.fromJson(cache, BaseResultEntity<T>);
//        T resultEntity = gson.fromJson(cache, getSuperclassTypeParameter(getClass()));
        return resultEntity.getData();
    }

    private Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowProgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing())
            pd.show();
    }

    /**
     * 隐藏加载框
     */
    private void dismissProgressDialog() {
        if (!isShowProgress()) return;
        if (pd != null && pd.isShowing())
            pd.dismiss();
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    private void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }
}
