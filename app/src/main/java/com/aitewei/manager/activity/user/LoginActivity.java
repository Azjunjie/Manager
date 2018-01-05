package com.aitewei.manager.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipListActivity;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetPrivilegeEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.utils.KeyBorardUtils;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.Md5Utils;
import com.aitewei.manager.utils.ScreenUtils;
import com.aitewei.manager.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_tool_bar_title)
    TextView tvToolBarTitle;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.bottom_tab)
    LinearLayout bottomTab;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        KeyBorardUtils.setupUI(activity, parentLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvToolBarTitle.getLayoutParams();
            layoutParams.topMargin = ScreenUtils.getStatusHeight(activity);
            tvToolBarTitle.setLayoutParams(layoutParams);
        }
        bottomTab.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                onLogin();
                break;
        }
    }

    private String accountStr;

    public void onLogin() {
        accountStr = etAccount.getText().toString();
        if (!TextUtils.isEmpty(accountStr)) {
            accountStr = accountStr.trim();
        }
        if (TextUtils.isEmpty(accountStr)) {
            ToastUtils.show(activity, "请输入账号");
            etAccount.requestFocus();
            return;
        }
        String pwdStr = etPwd.getText().toString();
        if (TextUtils.isEmpty(pwdStr)) {
            ToastUtils.show(activity, "请输入密码");
            etPwd.requestFocus();
            return;
        }
        String json = "{\"userName\":\"" + accountStr + "\",\"password\":\"" +
                Md5Utils.md5(pwdStr) + "\",\"token\":\"00000000\"}";
        LogUtil.e(json + "");
        showLoadingPopup();
        KeyBorardUtils.closeSoftKeyBorard(activity);
        RetrofitFactory.getInstance()
                .doLogin(json)
                .flatMap(new Function<BaseEntity, Observable<GetPrivilegeEntity>>() {
                    @Override
                    public Observable<GetPrivilegeEntity> apply(BaseEntity baseEntity) throws Exception {
                        String code = baseEntity.getCode();
                        if ("1".equals(code)) {
                            String params = "{\"userName\":\"" + accountStr + "\"}";
                            return RetrofitFactory.getInstance().doGetPrivileges(params);
                        }
                        throw new Exception(baseEntity.getMsg() + "");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetPrivilegeEntity>() {
                    @Override
                    public void accept(GetPrivilegeEntity getPrivilegeEntity) throws Exception {
                        dismissLoadingPopup();
                        if ("1".equals(getPrivilegeEntity.getCode())) {
                            ToastUtils.show(activity, "恭喜，登录成功！");
                            User user = User.newInstance();
                            user.setUserName(accountStr);
                            user.setData(getPrivilegeEntity.getData());
                            User.onSaveUser(activity);
                            startActivity(ShipListActivity.getIntent(activity));
                            finish();
                        } else {
                            ToastUtils.show(activity, getPrivilegeEntity.getMsg() + "");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissLoadingPopup();
                        ToastUtils.show(activity, throwable.getMessage() + "");
                    }
                });
    }

}
