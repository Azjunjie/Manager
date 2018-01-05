package com.aitewei.manager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 禁止滚动的listview
 */
public class NoScroolListView extends ListView {

    public NoScroolListView(Context context) {
        super(context);
    }

    public NoScroolListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScroolListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //不出现滚动条
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

}
