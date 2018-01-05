package com.aitewei.manager.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangjunjie on 2017/11/6.
 */

public class ToastUtils {

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_SHORT).show();
    }

    public static void showNetError(Context context) {
        Toast.makeText(context, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
    }

    public static void showPermission(Context context) {
        Toast.makeText(context, "抱歉，您暂时还没有操作权限", Toast.LENGTH_SHORT).show();
    }
}
