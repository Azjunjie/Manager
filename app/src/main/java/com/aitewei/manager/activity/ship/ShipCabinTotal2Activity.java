package com.aitewei.manager.activity.ship;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;

/**
 * 以卸船机为主的总进度
 */
public class ShipCabinTotal2Activity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_cabin_total2;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸船机总览");
    }

    @Override
    protected void initData() {

    }
}
