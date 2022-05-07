package com.aitewei.manager.common;

/**
 * 测试版
 * app配置的常量值
 * Created by Admin on 2018/2/26.
 */

public interface Config {
    //debug开关
    boolean isDebug = true;

    //bugly appid
    String BUGLY_APP_ID = "908374e9b8";

    //二维码地址
    String CODEURL = "http://81.70.196.208:8081/wcms/web/verification";
    //服务器地址
    String BASEURL = "http://81.70.196.208:8081/wcms/api/http/";
}
