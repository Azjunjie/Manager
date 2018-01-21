package com.aitewei.manager.common;

import android.app.Application;

import com.aitewei.manager.utils.LogUtil;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by zhangjunjie on 2017/10/31.
 */

public class ManagerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //日志管理
        LogUtil.init(true);
        //获取缓存的用户信息
        User.onReadUser(getApplicationContext());
        /**
         * 第二个参数appId
         * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：输出详细的Bugly SDK的Log；每一条Crash都会被立即上报；自定义日志将会在Logcat中输出。
         * 建议在测试阶段建议设置成true，发布时设置为false。
         */
        CrashReport.initCrashReport(getApplicationContext(), "11c8d0af3f", false);
    }
}
