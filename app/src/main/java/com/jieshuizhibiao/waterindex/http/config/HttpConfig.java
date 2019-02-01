package com.jieshuizhibiao.waterindex.http.config;

/**
 * Created by WanghongHe on 2018/12/3 12:13.
 */

public class HttpConfig {

    public static int HTTP_TIME = 30000;
    public static String BASE_URL = "http://www.jieshuizhibiao.com";//香港服务器不稳定

//  public static String BASE_URL = "https://c2c.sshsky.com";//开发环境


    public static final String HEAD_TOKEN_KEY = "TOKEN";
    public static final String HEAD_SYSTEM_KEY = "SYSTEM";
    public static final String HEAD_PRODUCT_KEY = "PRODUCT";
    public static final String HEAD_VERSION_KEY = "VERSION";
    public static final String HEAD_UUID_KEY = "CLIENT-UUID";

    public static final String ERROR_MSG="网络加载失败,请重试";
    public static final String BANK_TYPE = "1";
    public static final String ZFB_TYPE = "2";
    public static final String WX_TYPE = "3";

    public static final String TRANSCATION_RELEASE_All = "0";
    public static final String TRANSCATION_RELEASE_BUY = "1";
    public static final String TRANSCATION_RELEASE_SELL = "2";
}
