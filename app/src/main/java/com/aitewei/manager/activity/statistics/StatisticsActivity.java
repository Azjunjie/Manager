package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.Constant;
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

    @OnClick({R.id.btn_cargo_progress, R.id.btn_cabin_progress, R.id.btn_unloader_statistics, R.id.btn_unloader_team_statistics
            , R.id.btn_cargo_efficiency, R.id.btn_carbin_efficiency})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cargo_progress://船舱货物进度统计
                startActivity(ShipListChoiceActivity.getIntent(this, Constant.TYPE_STATISTICS_CARGO_PROGRESS));
                break;
            case R.id.btn_cabin_progress://船舱舱口进度统计
                startActivity(ShipListChoiceActivity.getIntent(this, Constant.TYPE_STATISTICS_CABIN_PROGRESS));
                break;
            case R.id.btn_unloader_statistics://卸船机作业量统计
                startActivity(ShipListChoiceActivity.getIntent(this, Constant.TYPE_STATISTICS_UNLOADER_PROGRESS));
                break;
            case R.id.btn_unloader_team_statistics://卸船机班次统计
                startActivity(UnloaderTeamActivity.getIntent(this));
                break;
            case R.id.btn_cargo_efficiency://船舱货物效率统计
                startActivity(ShipListChoiceActivity.getIntent(this, Constant.TYPE_STATISTICS_CARGO_EFFICAIENCY));
                break;
            case R.id.btn_carbin_efficiency://船舱舱口效率统计
                startActivity(ShipListChoiceActivity.getIntent(this, Constant.TYPE_STATISTICS_CABIN_EFFICAIENCY));
                break;
        }
    }
}
