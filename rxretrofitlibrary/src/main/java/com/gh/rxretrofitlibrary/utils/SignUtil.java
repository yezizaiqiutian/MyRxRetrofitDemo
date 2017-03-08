package com.gh.rxretrofitlibrary.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 跟上签名工具类
 * Created by 1 on 2016/5/13.
 */
public class SignUtil {

    /**
     * 使用secret对paramValues按以下算法进行签名：
     * uppercase(hex(md5(secretkey1value1key2value2...secret))
     *
     * @param paramValues  参数列表
     * @param secret 密钥
     * @return
     */
    public static String sign(Map<String,Object> paramValues,
                              String secret) throws SignatureException {
        return sign(paramValues, null, secret);
    }

    /**
     * 对paramValues进行签名，其中ignoreParamNames这些参数不参与签名
     *
     * @param paramValues
     * @param ignoreParamNames
     * @param secret
     * @return
     */
    public static String sign(Map<String,Object> paramValues,
                              List<String> ignoreParamNames, String secret) throws SignatureException {
        try {
            HashMap signMap = new HashMap();
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
                for (String ignoreParamName : ignoreParamNames) {
                    paramNames.remove(ignoreParamName);
                }
            }
            Collections.sort(paramNames);

            sb.append(secret);
            for (String paramName : paramNames) {
                sb.append(paramName).append(paramValues.get(paramName));
            }
            sb.append(secret);
            byte[] md5Digest = getMD5Digest(sb.toString());
            String sign = byte2hex(md5Digest);
            signMap.put("appParam", sb.toString());
            signMap.put("appSign", sign);
            return sign;
        } catch (IOException e) {
            throw new SignatureException("加密签名计算失败", e);
        }
    }

    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }
/**************************************************钱包签名*****************************************************/
private static String toHexValue(byte[] messageDigest) {
    if (messageDigest == null)
        return "";
    StringBuilder hexValue = new StringBuilder();
    for (byte aMessageDigest : messageDigest) {
        int val = 0xFF & aMessageDigest;
        if (val < 16) {
            hexValue.append("0");
        }
        hexValue.append(Integer.toHexString(val));
    }
    return hexValue.toString();
}

    /**
     *
     * @param params
     * @return
     */
    public static String sign(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer sb = new StringBuffer();
        int count = keys.size();
        for (String s : keys) {
            sb.append(s);
            sb.append(":");
            if (!hasNullStr(params.get(s))) {
                sb.append(params.get(s));
            }
            if (count > 1) {
                sb.append("|");
            }
            count--;
        }
        String sign = "";
        try {
            sign = toHexValue(encryptMD5(sb.toString().getBytes(Charset.forName("utf-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("md5 error");
        }
        return sign;
    }


    private static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
    private static boolean hasNullStr(String arg) {
        return arg == null || arg.trim().equals("")
                || arg.trim().equalsIgnoreCase("null");
    }

    public static Map<String, String> getWalletDefaultMap(String timestamp) {
        Map<String, String> map = new HashMap<>();
        map.put(PARTNERID_KEY, PARTNERID);
        map.put(TIMESTAMP__WALLET, timestamp);
        map.put(SECKEY, SECKEY_VALUE);//
        return map;
    }

    public static final String SECKEY_VALUE = "323b686d-0fc0-4261-bda5-31a695a49359";//钱包通讯密钥
    public static final String PARTNERID = "daoyoubao";//钱包公共参数的渠道标识
    public static final String PARTNERID_KEY = "partnerId";//钱包公共参数的渠道标识参数字段
    public static final String TIMESTAMP__WALLET = "timeStamp";//时间戳,当前时间，格式（yyyyMMddHHmmss）,长度14
    public static final String SECKEY = "seckey";//通信密钥KEY
}
