package com.aitewei.manager.activity.statistics;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class Statistics1Activity extends BaseActivity {
    private static final int REQUEST_SHIP_LIST = 1;

    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_team)
    TextView tvTeam;

    private String currentDate;

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
        initDatePicker();
    }

    @OnClick({R.id.tv_ship_name, R.id.tv_time, R.id.tv_team, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ship_name:
//                showShipListDialog();
                startActivityForResult(AllShipListActivity.getIntent(activity), REQUEST_SHIP_LIST);
                break;
            case R.id.tv_time:
                String time = tvTime.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    time = currentDate;
                }
                datePicker.show(time);
                break;
            case R.id.tv_team:
                showTeamListDialog();
                break;
            case R.id.btn_search:
                startActivity(new Intent(activity, StatisticsResult1Activity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_SHIP_LIST == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                String shipName = data.getStringExtra("shipName");
                tvShipName.setText(shipName + "");
            }
        }
    }

    private CustomDatePicker datePicker;

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        currentDate = sdf.format(new Date());
        datePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTime.setText(time.split(" ")[0]);
            }
        }, "1970-01-01 00:00", currentDate); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        datePicker.showSpecificTime(false); // 不显示时和分
        datePicker.setIsLoop(false); // 不允许循环滚动
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
