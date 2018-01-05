package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 统计界面
 */
public class StatisticsActivity extends BaseActivity {

    @BindView(R.id.btn_statistics)
    LinearLayout btnStatistics;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, StatisticsActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "统计");
        btnStatistics.setSelected(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onClickStatistics() {

    }

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(activity, Statistics1Activity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(activity, Statistics2Activity.class));
                break;
        }
    }
}
