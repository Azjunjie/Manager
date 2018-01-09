package com.aitewei.manager.activity.statistics;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class Statistics1Activity extends BaseActivity {
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_team)
    TextView tvTeam;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics1;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "班组信息");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_ship_name, R.id.tv_team, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ship_name:
                showShipListDialog();
                break;
            case R.id.tv_team:
                showTeamListDialog();
                break;
            case R.id.btn_search:
                startActivity(new Intent(activity, StatisticsResult1Activity.class));
                break;
        }
    }

    private String[] ships = new String[]{"船舶一", "船舶二", "船舶三", "船舶四"};
    private AlertDialog shipDialog;

    private void showShipListDialog() {
        if (shipDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setSingleChoiceItems(ships, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvShipName.setText(ships[which] + "");
                    dialog.dismiss();
                }
            });
            shipDialog = builder.setTitle("请选择查询的船舶")
                    .setIcon(R.mipmap.ic_launcher)
                    .create();
        }
        shipDialog.show();
    }

    private String[] teams = new String[]{"白班", "夜班"};
    private AlertDialog teamDialog;

    private void showTeamListDialog() {
        if (teamDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setSingleChoiceItems(teams, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvTeam.setText(teams[which] + "");
                    dialog.dismiss();
                }
            });
            teamDialog = builder.setTitle("请选择查询的班次")
                    .setIcon(R.mipmap.ic_launcher)
                    .create();
        }
        teamDialog.show();
    }

}
