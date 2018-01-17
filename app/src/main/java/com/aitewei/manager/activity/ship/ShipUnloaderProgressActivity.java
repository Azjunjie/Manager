package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 以卸船机为主的总进度
 */
public class ShipUnloaderProgressActivity extends BaseActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_team)
    TextView tvTeam;
    @BindView(R.id.popup_container)
    LinearLayout popupContainer;

    private String taskId;
    private String currentDate;
    private String selectDate;//选择的日期
    private String selectTeam;//选择的班次

    public static Intent getIntent(Context context, String taskId) {
        Intent intent = new Intent(context, ShipUnloaderProgressActivity.class);
        intent.putExtra("taskId", taskId);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_unloader_progress;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸船机总览");
    }

    @Override
    protected void initData() {
        taskId = getIntent().getStringExtra("taskId");

        initDatePicker();
    }

    @OnClick({R.id.btn_popup, R.id.tv_time, R.id.tv_team
            , R.id.btn_clear, R.id.btn_confirm, R.id.btn_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_popup://显示筛选弹窗
                popupContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_time://选择日期
                String time = tvTime.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    time = currentDate;
                }
                datePicker.show(time);
                break;
            case R.id.tv_team://选择班次
                showTeamListDialog();
                break;
            case R.id.btn_clear://清空筛选
                tvTime.setText("");
                tvTeam.setText("");
                tvInfo.setText("全部");
                popupContainer.setVisibility(View.GONE);
                break;
            case R.id.btn_confirm://确认筛选
                if (TextUtils.isEmpty(selectDate)) {
                    ToastUtils.show(activity, "请选择时间");
                    return;
                }
                if (TextUtils.isEmpty(selectTeam)) {
                    ToastUtils.show(activity, "请选择班次");
                    return;
                }
                tvInfo.setText(selectDate + "----" + selectTeam);
                popupContainer.setVisibility(View.GONE);
                break;
            case R.id.btn_empty://隐藏弹窗
                popupContainer.setVisibility(View.GONE);
                break;
        }
    }

    private CustomDatePicker datePicker;

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        currentDate = sdf.format(new Date());
        datePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                selectDate = time.split(" ")[0];
                tvTime.setText(selectDate);
            }
        }, "1970-01-01 00:00", currentDate); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        datePicker.showSpecificTime(false); // 不显示时和分
        datePicker.setIsLoop(false); // 不允许循环滚动
    }

    private String[] teams = new String[]{"白班", "夜班"};
    private AlertDialog teamDialog;

    private void showTeamListDialog() {
        if (teamDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setSingleChoiceItems(teams, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectTeam = teams[which] + "";
                    tvTeam.setText(selectTeam);
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
