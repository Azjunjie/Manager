package com.aitewei.manager.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;

/**
 * 设置标题栏
 */
public class ToolBarUtil {

    public static void init(final BaseActivity activity, @NonNull String title) {
        FrameLayout toolBar = activity.findViewById(R.id.tool_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams layoutParams = toolBar.getLayoutParams();
            if (layoutParams instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layoutParams;
                params.topMargin = ScreenUtils.getStatusHeight(activity);
                toolBar.setLayoutParams(params);
            } else if (layoutParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutParams;
                params.topMargin = ScreenUtils.getStatusHeight(activity);
                toolBar.setLayoutParams(params);
            } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutParams;
                params.topMargin = ScreenUtils.getStatusHeight(activity);
                toolBar.setLayoutParams(params);
            }
        }
        toolBar.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        TextView toolBarTitle = toolBar.findViewById(R.id.tv_tool_bar_title);
        toolBarTitle.setText(title);
    }

    /**
     * 设置透明的状态栏
     */
    public static ImmersionBar setTransparentStatusBar(BaseActivity activity
            , boolean isTransparent) {
        ImmersionBar immersionBar = ImmersionBar.with(activity)
                .transparentStatusBar();
        if (!isTransparent) {
            immersionBar.statusBarColor(R.color.blue);//状态栏颜色，不写默认透明色
        }
        immersionBar.statusBarDarkFont(true, 0.4f)
                .flymeOSStatusBarFontColor(R.color.colorAccent)
                .navigationBarEnable(false)
                .keyboardEnable(true);
        immersionBar.init();
        return immersionBar;
    }

}
