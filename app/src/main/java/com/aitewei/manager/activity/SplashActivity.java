package com.aitewei.manager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipListActivity;
import com.aitewei.manager.activity.user.LoginActivity;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * app启动页
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.bottom_tab)
    LinearLayout bottomTab;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        bottomTab.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        Disposable disposable = Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (User.isLogin()) {
                            startActivity(ShipListActivity.getIntent(activity));
                        } else {
                            startActivity(LoginActivity.getIntent(activity));
                        }
                        finish();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected boolean isSupportStatusColorSet() {
        return false;
    }

}
