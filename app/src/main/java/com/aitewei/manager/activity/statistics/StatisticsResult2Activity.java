package com.aitewei.manager.activity.statistics;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;

public class StatisticsResult2Activity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics_result2;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity,"船舱信息查询");
    }

    @Override
    protected void initData() {

    }
}
