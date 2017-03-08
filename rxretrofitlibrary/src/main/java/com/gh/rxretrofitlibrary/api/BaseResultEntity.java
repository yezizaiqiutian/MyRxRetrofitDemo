package com.gh.rxretrofitlibrary.api;

/**
 * @author: gh
 * @description: 回调信息统一封装类
 * @date: 2017/2/27 17:04
 * @note:
 */

public class BaseResultEntity<T> {

    /**
     * 判断标识
     */
    private int ret;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 显示数据(用户需要关心的数据)
     */
    private T data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
