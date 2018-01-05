package com.aitewei.manager.common;

import android.app.Application;

import com.aitewei.manager.utils.LogUtil;

/**
 * Created by zhangjunjie on 2017/10/31.
 */

public class ManagerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //日志管理
        LogUtil.init(true);
        User.onReadUser(getApplicationContext());
    }
}
