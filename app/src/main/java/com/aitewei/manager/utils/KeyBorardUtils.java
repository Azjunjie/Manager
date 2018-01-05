package com.aitewei.manager.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 */

public class KeyBorardUtils {

    public static void closeSoftKeyBorard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow()
                            .getDecorView().getWindowToken()
                    , 0);
        }
    }

    /**
     * 在EditText之外触摸 ，隐藏键盘，失去焦点
     *
     * @param view
     */
    public static void setupUI(final Activity activity, final View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    closeSoftKeyBorard(activity);
                    view.setFocusable(true);
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(activity, innerView);
            }
        }
    }

}
