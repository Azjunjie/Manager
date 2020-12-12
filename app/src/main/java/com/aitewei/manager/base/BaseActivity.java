package com.aitewei.manager.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.statistics.StatisticsActivity;
import com.aitewei.manager.common.PermissionsCode;
import com.aitewei.manager.common.Popup;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.utils.ToolBarUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {
    protected static String FINISH_ACTIVITY_INTENT_ACTION = "finishActivity";

    protected BaseActivity activity;

    //用于butterKnife解绑
    private Unbinder unbinder;
    //管理Destroy取消订阅者
    protected CompositeDisposable compositeDisposable;
    private ImmersionBar immersionBar;
    private FinishActivityReceiver activityReceiver;

//    @BindView(R.id.line)
//    View line;
//    @BindView(R.id.btn_statistics)
//    LinearLayout btnStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        this.activity = this;
        unbinder = ButterKnife.bind(this);
        if (isSupportRx()) {
            compositeDisposable = new CompositeDisposable();
        }
        if (isSupportStatusColorSet()) {
            immersionBar = ToolBarUtil
                    .setTransparentStatusBar(this, false);
        } else {
            immersionBar = ToolBarUtil
                    .setTransparentStatusBar(this, true);
        }
        activityReceiver = new FinishActivityReceiver();
        registerReceiver(activityReceiver, new IntentFilter(FINISH_ACTIVITY_INTENT_ACTION));
        initView();
        initData();

//        if (PermissionsCode.isHasPermission(PermissionsCode.watch)) {
//            line.setVisibility(View.VISIBLE);
//            btnStatistics.setVisibility(View.VISIBLE);
//        } else {
//            line.setVisibility(View.GONE);
//            btnStatistics.setVisibility(View.GONE);
//        }
    }

    /**
     * @return 界面布局资源id
     */
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 设置页面是否支持状态栏设置
     *
     * @return true-设置状态栏颜色等，false-透明的状态栏
     */
    protected boolean isSupportStatusColorSet() {
        return true;
    }

    /**
     * 页面是否使用了rxjava
     *
     * @return 默认true-实例CompositeDisposable
     */
    protected boolean isSupportRx() {
        return true;
    }

    private Popup loadingPopup;

    protected void showLoadingPopup() {
        if (loadingPopup == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.popup_loading, null);
            loadingPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    .setmContentView(view)
                    .setmFocusable(false)
                    .setmOutsideTouchable(false)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        loadingPopup.showAtLocation(this, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

    protected void dismissLoadingPopup() {
        if (loadingPopup != null && loadingPopup.isShowing()) {
            loadingPopup.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        if (activityReceiver != null) {
            unregisterReceiver(activityReceiver);
        }
    }

    @Override
    public void onBackPressed() {
        if (loadingPopup != null && loadingPopup.isShowing()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 退出当前页面的广播接收者
     */
    private class FinishActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finishActivity();
        }
    }

    protected void finishActivity() {
        finish();
    }

    @OnClick({R.id.btn_task, R.id.btn_statistics})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_task://任务
                onClickTask();
                break;
            case R.id.btn_statistics://统计
                if (PermissionsCode.isHasPermission(PermissionsCode.watch)) {
                    onClickStatistics();
                } else {
                    ToastUtils.show(this, "抱歉，暂无操作权限！");
                }
                break;
        }
    }

    /**
     * 任务
     */
    protected void onClickTask() {
        sendBroadcast(new Intent(FINISH_ACTIVITY_INTENT_ACTION));
    }

    /**
     * 统计
     */
    protected void onClickStatistics() {
        startActivity(StatisticsActivity.getIntent(activity));
    }

}
