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

public class Statistics2Activity extends BaseActivity {
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;

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

    @OnClick({R.id.tv_ship_name, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ship_name:
                showShipListDialog();
                break;
            case R.id.btn_search:
                startActivity(new Intent(activity, StatisticsResult2Activity.class));
                break;
        }
    }

    private String[] ships = new String[]{"船舶一", "船舶二", "船舶三", "船舶四"};
    private AlertDialog dialog;

    private void showShipListDialog() {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setSingleChoiceItems(ships, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvShipName.setText(ships[which] + "");
                    dialog.dismiss();
                }
            });
            dialog = builder.setTitle("请选择需要查询的船舶")
                    .setIcon(R.mipmap.ic_launcher)
                    .create();
        }
        dialog.show();
    }

}
