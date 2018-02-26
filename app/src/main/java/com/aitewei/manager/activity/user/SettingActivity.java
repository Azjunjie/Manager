package com.aitewei.manager.activity.user;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.Popup;
import com.aitewei.manager.common.User;
import com.aitewei.manager.utils.AppInfoUtils;
import com.aitewei.manager.utils.ToolBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "我的");
    }

    @Override
    protected void initData() {
        String userName = User.newInstance().getUserName();
        tvName.setText("用户名：" + userName + "");
        if ("jhy".equals(userName)) {
            tvType.setText("类别：计划员");
        } else if ("zdy".equals(userName)) {
            tvType.setText("类别：指导员");
        } else if ("ptry".equals(userName)) {
            tvType.setText("类别：普通人员");
        } else {
            tvType.setText("类别：计划员");
        }
        tvVersion.setText("版本号" + AppInfoUtils.getVersionName(activity));
    }

    @OnClick({R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                showExitPopup();
                break;
        }
    }

    private Popup exitPopup;

    private void showExitPopup() {
        if (exitPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm_cancle, null);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            tvTitle.setText("确定退出登录？");
            view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exitPopup.dismiss();
                }
            });
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File file = new File(activity.getCacheDir(), "user.data");
                    if (file.exists()) {
                        file.delete();
                    }
                    User.newInstance().setUserName("");
                    exitPopup.dismiss();
                    startActivity(new Intent(activity, LoginActivity.class));
                    EventBus.getDefault().post("ExitLogin");
                    finish();
                }
            });
            exitPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        exitPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }
}
