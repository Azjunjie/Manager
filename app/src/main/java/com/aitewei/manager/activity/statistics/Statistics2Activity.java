package com.aitewei.manager.activity.statistics;

import android.content.Intent;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;

import butterknife.OnClick;

public class Statistics2Activity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics2;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity,"船舱信息查询");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        startActivity(new Intent(activity, StatisticsResult2Activity.class));
    }
}
