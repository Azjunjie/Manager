package com.aitewei.manager.activity.statistics;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;

public class StatisticsResult1Activity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics_result1;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity,"班组信息");
    }

    @Override
    protected void initData() {

    }
}
