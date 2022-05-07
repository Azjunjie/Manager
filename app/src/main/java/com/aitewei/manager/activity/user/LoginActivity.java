package com.aitewei.manager.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipListActivity;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.common.Config;
import com.aitewei.manager.common.GlideImageLoader;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetPrivilegeEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.utils.KeyBorardUtils;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.Md5Utils;
import com.aitewei.manager.utils.ScreenUtils;
import com.aitewei.manager.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.tencent.bugly.beta.Beta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;

    private Handler handler;
    private int downTime = 60;//倒计时60s

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

    @OnClick({R.id.btn_login, R.id.btn_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                onLogin();
                break;
            case R.id.btn_code://获取验证码
                if (btnCode.isEnabled()) {
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

                    btnCode.setEnabled(false);
                    btnCode.setText(downTime + "s");
                    requestCode();
                    if (handler == null) {
                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 1://倒计时-1
                                        if (downTime <= 1) {
                                            downTime = 60;
                                            btnCode.setEnabled(true);
                                            btnCode.setText("获取验证码");
                                            return;
                                        }
                                        downTime--;
                                        btnCode.setText(downTime + "s");
                                        handler.sendEmptyMessageDelayed(1, 1000);
                                        break;
                                }
                            }
                        };
                    }
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
                break;
        }
    }

    private Thread thread;
    private String accessToken;

    /**
     * 获取二维码
     */
    private void requestCode() {
        thread = new Thread(new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Config.CODEURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        accessToken = connection.getHeaderField("access-token");
                        LogUtil.e("accessToken=" + accessToken);
                        InputStream is = connection.getInputStream();
                        byte[] bytes = readStream(is);
                        runOnUiThread(() -> {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ivCode.setImageBitmap(bitmap);
                            ivCode.setVisibility(View.VISIBLE);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /*
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
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
        String codeStr = etCode.getText().toString();
        if (TextUtils.isEmpty(codeStr)) {
            ToastUtils.show(activity, "请输入验证码");
            etCode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(accessToken)) {
            ToastUtils.show(activity, "请获取验证码");
            return;
        }
        String json = "{\"userName\":\"" + accountStr + "\",\"password\":\"" +
                Md5Utils.md5(pwdStr) + "\",\"token\":\"" + codeStr + "\",\"Access-Token\":\"" + accessToken + "\"}";
        LogUtil.e(json + "");
        showLoadingPopup();
        KeyBorardUtils.closeSoftKeyBorard(activity);
        RetrofitFactory.getInstance()
                .doLogin(json)
                .flatMap((Function<BaseEntity, Observable<GetPrivilegeEntity>>) baseEntity -> {
                    String code = baseEntity.getCode();
                    if ("1".equals(code)) {
                        String params = "{\"userName\":\"" + accountStr + "\"}";
                        return RetrofitFactory.getInstance().doGetPrivileges(params);
                    }
                    throw new Exception(baseEntity.getMsg() + "");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getPrivilegeEntity -> {
                    dismissLoadingPopup();
                    if ("1".equals(getPrivilegeEntity.getCode())) {
                        ToastUtils.show(activity, "恭喜，登录成功！");
                        User user = User.newInstance();
                        user.setUserName(accountStr);
                        user.setData(getPrivilegeEntity.getData());
                        User.onSaveUser(activity.getApplicationContext());
                        startActivity(ShipListActivity.getIntent(activity));
                        finish();
                    } else {
                        ToastUtils.show(activity, getPrivilegeEntity.getMsg() + "");
                    }
                }, throwable -> {
                    dismissLoadingPopup();
                    ToastUtils.show(activity, throwable.getMessage() + "");
                });
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeMessages(1);
        }
        super.onDestroy();
    }
}
