package com.aitewei.manager.activity.ship;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.utils.ToolBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 以卸船机为主的总进度
 */
public class ShipCabinTotal2Activity extends BaseActivity {
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tv_team)
    TextView tvTeam;

    private boolean isChecked;//是否筛选

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
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShipCabinTotal2Activity.this.isChecked = isChecked;
            }
        });
    }

    @OnClick({R.id.tv_time, R.id.tv_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                if (isChecked) {

                } else {
                    ToastUtils.show(activity, "请先确认是否筛选");
                }
                break;
            case R.id.tv_team:
                if (isChecked) {
                    showTeamListDialog();
                } else {
                    ToastUtils.show(activity, "请先确认是否筛选");
                }
                break;
        }
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
            teamDialog = builder.setTitle("请选择需要查询的班次")
                    .setIcon(R.mipmap.ic_launcher)
                    .create();
        }
        teamDialog.show();
    }

}
