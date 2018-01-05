package com.aitewei.manager.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.aitewei.manager.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取app信息工具类
 */
public class AppInfoUtils {

    /**
     * 获取设备imei
     */
    public static String getDeviceImei(BaseActivity activity, int requestCode) {
        TelephonyManager mTm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, requestCode);
                return "";
            }
        }
        return mTm.getDeviceId();
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本号信息
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * @return 安装渠道名字
     */
    public static String getChannelName(Context context) {
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,
                // 因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo
                        (context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     *
     * @return 安装渠道Id
     */
    public static String getChannelId(Context context) {
        //渠道名
        String channelName = getChannelName(context);
        Map<String, Integer> idMaps = new HashMap<>();
        idMaps.put("yingyongbao", 0);
        idMaps.put("xiaomi", 1);
        idMaps.put("shoujizhushou360", 2);
        idMaps.put("baidu", 3);
        idMaps.put("zhushou91", 4);
        idMaps.put("androidshichang", 5);
        idMaps.put("wandoujia", 6);
        idMaps.put("ppzhushou", 7);
        idMaps.put("aliapps", 8);
        idMaps.put("meizu", 9);
        idMaps.put("oppo", 10);
        idMaps.put("vivo", 11);
        idMaps.put("huawei", 12);
        idMaps.put("chuizi", 13);
        idMaps.put("jinli", 14);
        idMaps.put("aiqiyi", 15);
        idMaps.put("leshi", 16);
        return idMaps.containsKey(channelName) ? idMaps.get(channelName) + "" : -1 + "";
    }


}
