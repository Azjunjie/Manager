package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.statistics.StatisticsActivity;
import com.aitewei.manager.activity.user.SettingActivity;
import com.aitewei.manager.adapter.FragmentViewPagerAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseFragment;
import com.aitewei.manager.common.User;
import com.aitewei.manager.fragment.ShipListNewFragment;
import com.aitewei.manager.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 船舶列表
 */
public class ShipListActivity extends BaseActivity {

    @BindView(R.id.btn_plc)
    TextView btnPlc;
    @BindView(R.id.tool_bar)
    FrameLayout toolBar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.btn_task)
    LinearLayout btnTask;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ShipListActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_list;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolBar.getLayoutParams();
            layoutParams.topMargin = ScreenUtils.getStatusHeight(activity);
            toolBar.setLayoutParams(layoutParams);
        }
        btnTask.setSelected(true);

        String userName = User.newInstance().getUserName();
        if ("zdy".equals(userName)) {
            btnPlc.setVisibility(View.VISIBLE);
        } else {
            btnPlc.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(ShipListNewFragment.newInstance());

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @OnClick({R.id.btn_plc, R.id.btn_mine, R.id.btn_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_plc:
                startActivity(ShipUnLoaderParamDetailListActivity.getIntent(activity));
                break;
            case R.id.btn_mine:
                startActivity(SettingActivity.getIntent(activity));
                break;
            case R.id.btn_statistics://统计
                startActivity(StatisticsActivity.getIntent(activity));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String tag) {
        if ("ExitLogin".equals(tag)) {//用户点击退出登录
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void finishActivity() {

    }

    @Override
    protected void onClickTask() {

    }

}
