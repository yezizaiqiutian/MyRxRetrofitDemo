package com.gh.rxretrofitlibrary.exception;

/**
 * @author: gh
 * @description: 自定义错误信息，统一处理返回处理
 * @date: 2017/2/27 17:42
 * @note:// TODO: 2017/2/28 对错误的封装处理还可优化 
 */

public class HttpTimeException extends RuntimeException{

    public static final int NO_DATA = 0x2;

    public HttpTimeException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public HttpTimeException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 转换错误数据
     *
     * @param code  服务器返回的错误码,根据错误码进行判断,可增加
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;

        }
        return message;
    }

}
