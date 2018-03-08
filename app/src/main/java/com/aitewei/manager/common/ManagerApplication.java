package com.aitewei.manager.common;

import android.app.Application;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipListActivity;
import com.aitewei.manager.activity.user.LoginActivity;
import com.aitewei.manager.utils.LogUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by zhangjunjie on 2017/10/31.
 */

public class ManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //日志管理
        LogUtil.init(Config.isDebug);
        //获取缓存的用户信息
        User.onReadUser(getApplicationContext());
        //初始化bugly
        initBugly();
    }

    private void initBugly() {
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
//        Beta.initDelay = 1 * 1000;
        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 60 * 1000;
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.mipmap.ic_launcher;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;
        /**
         * 只允许在指定的Activity上显示更新弹窗，其他activity上不显示弹窗
         * ; 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(LoginActivity.class);
        Beta.canShowUpgradeActs.add(ShipListActivity.class);
        /**
         * 第二个参数appId
         * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：输出详细的Bugly SDK的Log；每一条Crash都会被立即上报
         * ；自定义日志将会在Logcat中输出。是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
         */
        Bugly.init(getApplicationContext(), Config.BUGLY_APP_ID, Config.isDebug);
    }
}
