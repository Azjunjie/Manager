package com.aitewei.manager.utils;

import android.util.Log;

import io.reactivex.annotations.NonNull;

/**
 * 日志工具类
 * Created by zjj on 2017/10/30.
 */

public class LogUtil {
    private static String TAG = LogUtil.class.getName();

    private static boolean flag;

    public static void init(boolean flag) {
        LogUtil.flag = flag;
    }

    public static void e(@NonNull String msg) {
        if (flag)
            Log.e(TAG, msg);
    }

    public static void e(@NonNull String tag, @NonNull String msg) {
        if (flag)
            Log.e(tag, msg);
    }
}
