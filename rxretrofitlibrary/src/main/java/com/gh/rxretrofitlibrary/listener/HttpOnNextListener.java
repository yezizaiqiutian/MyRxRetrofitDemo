package com.gh.rxretrofitlibrary.listener;

/**
 * @author: gh
 * @description: 成功回调处理
 * @date: 2017/2/27 17:09
 * @note:
 */

public abstract class HttpOnNextListener<T> {

    /**
     * 成功后回调方法
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 缓存回调结果
     * @param t
     * @param explain   返回缓存的原因
     */
    public void onCacheNext(T t,String explain){

    }

    /**
     * 缓存回调结果
     * @param s
     * @param explain   返回缓存的原因
     */
    public void onCacheNext(String s,String explain){

    }

    /**
     * 失败或错误回调方法
     * @param e
     */
    public void onError(Throwable e) {

    }

    /**
     * 取消回调方法
     */
    public void onCancel() {

    }
}
