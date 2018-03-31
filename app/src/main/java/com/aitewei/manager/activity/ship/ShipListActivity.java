package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.statistics.StatisticsActivity;
import com.aitewei.manager.activity.user.SettingActivity;
import com.aitewei.manager.adapter.FragmentViewPagerAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseFragment;
import com.aitewei.manager.common.GlideImageLoader;
import com.aitewei.manager.fragment.FragmentListNewFragment;
import com.aitewei.manager.utils.ScreenUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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

    @BindView(R.id.tool_bar)
    FrameLayout toolBar;
    @BindView(R.id.banner)
    Banner banner;

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
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenWidth(activity) / 16f * 9);
        banner.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        initBanner();

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(FragmentListNewFragment.newInstance());

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void initBanner() {
        List<Integer> imgList = new ArrayList<>();
        imgList.add(R.drawable.bg_banner2);
        imgList.add(R.drawable.bg_banner4);
        banner.setImageLoader(new GlideImageLoader());
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImages(imgList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @OnClick({R.id.btn_mine, R.id.btn_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mine:
                startActivity(SettingActivity.getIntent(activity));
                break;
            case R.id.btn_statistics://统计
                startActivity(StatisticsActivity.getIntent(activity));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
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
