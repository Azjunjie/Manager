package com.aitewei.manager.common;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ScreenUtils;

/**
 * popupWindow弹窗
 * Created by zjj on 2017/11/1.
 */

public class Popup {

    private PopupWindow popupWindow;

    private View mContentView;
    private Drawable mBackground;
    private boolean mFocusable;
    private boolean mTouchable;
    private boolean mOutsideTouchable;
    private float mBackgroundAlpha;
    private PopupWindow.OnDismissListener dismissListener;
    private int width;
    private int height;
    private int animStyleId;

    public Popup(Builder builder) {
        mContentView = builder.mContentView;
        mBackground = builder.mBackground;
        mFocusable = builder.mFocusable;
        mTouchable = builder.mTouchable;
        mOutsideTouchable = builder.mOutsideTouchable;
        mBackgroundAlpha = builder.mBackgroundAlpha;
        dismissListener = builder.dismissListener;
        width = builder.width;
        height = builder.height;
        animStyleId = builder.animStyleId;

        popupWindow = new PopupWindow(width, height);
        popupWindow.setContentView(mContentView);
        popupWindow.setBackgroundDrawable(mBackground);
        popupWindow.setFocusable(mFocusable);
        popupWindow.setTouchable(mTouchable);
        popupWindow.setOutsideTouchable(mOutsideTouchable);
        popupWindow.setOnDismissListener(dismissListener);
        if (animStyleId > 0) {
            popupWindow.setAnimationStyle(animStyleId);
        }
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        popupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void showAtLocation(BaseActivity activity, View parent, int gravity, int x, int y) {
        ScreenUtils.setBackgroundAlpha(activity, mBackgroundAlpha);
        popupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public static final class Builder {
        private View mContentView;
        private Drawable mBackground;
        private boolean mFocusable;
        private boolean mTouchable;
        private boolean mOutsideTouchable;
        private float mBackgroundAlpha;
        private PopupWindow.OnDismissListener dismissListener;
        private int width;
        private int height;
        private int animStyleId;

        public Builder() {
            this.mBackground = new BitmapDrawable();
            this.mFocusable = true;
            this.mTouchable = true;
            this.mOutsideTouchable = true;
            this.mBackgroundAlpha = 1;
            this.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            this.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        public Builder(View contentView) {
            this();
            this.mContentView = contentView;
        }

        public Builder setmContentView(View mContentView) {
            this.mContentView = mContentView;
            return this;
        }

        public Builder setmBackground(Drawable mBackground) {
            this.mBackground = mBackground;
            return this;
        }

        public Builder setmFocusable(boolean mFocusable) {
            this.mFocusable = mFocusable;
            return this;
        }

        public Builder setmTouchable(boolean mTouchable) {
            this.mTouchable = mTouchable;
            return this;
        }

        public Builder setmOutsideTouchable(boolean mOutsideTouchable) {
            this.mOutsideTouchable = mOutsideTouchable;
            return this;
        }

        public Builder setmBackgroundAlpha(final BaseActivity activity, float mBackgroundAlpha) {
            this.mBackgroundAlpha = mBackgroundAlpha;
            this.dismissListener = new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ScreenUtils.setBackgroundAlpha(activity, 1f);
                }
            };
            return this;
        }

        public Builder setLayoutParam(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setAnimStyleId(int animStyleId) {
            this.animStyleId = animStyleId;
            return this;
        }

        public Popup build() {
            return new Popup(this);
        }

    }

}
