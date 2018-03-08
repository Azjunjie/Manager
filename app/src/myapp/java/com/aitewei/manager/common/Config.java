package com.aitewei.manager.common;

/**
 * 线上版
 * app配置的常量值
 * Created by Admin on 2018/2/26.
 */

public interface Config {
    //debug开关
    boolean isDebug = false;

    //bugly appid
    String BUGLY_APP_ID = "11c8d0af3f";

    //服务器地址
    String BASEURL = "http://60.2.201.70:8086/wcms/api/http/";
}
