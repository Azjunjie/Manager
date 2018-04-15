package com.aitewei.manager.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipCabinListActivity;
import com.aitewei.manager.adapter.ShipListNewAdapter;
import com.aitewei.manager.base.BaseFragment;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.GlideImageLoader;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.ShipListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ScreenUtils;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.view.LoadGroupView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 新的船舶列表页--作业船舶和预靠船舶放在一起
 */
public class ShipListNewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list_view)
    RecyclerView listView;
    private ShipListNewAdapter adapter;
    private Banner banner;

    public static ShipListNewFragment newInstance() {
        ShipListNewFragment shipListFragment = new ShipListNewFragment();
        return shipListFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fragment_list_new;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        refreshLayout.setOnRefreshListener(this);

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        adapter = new ShipListNewAdapter(R.layout.layout_ship_list_new_item, null);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ShipListEntity.DataBean> dataList = adapter.getData();
                ShipListEntity.DataBean bean = dataList.get(position);
                String id = bean.getId();
                if (!TextUtils.isEmpty(id)) {
                    startActivity(ShipCabinListActivity.getIntent(activity, bean.getShipType(), id, bean.getShipName()));
                }
            }
        });

        View view = LayoutInflater.from(activity).inflate(R.layout.layout_ship_list_head_item, null);
        banner = view.findViewById(R.id.banner);
        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenWidth(activity) / 16f * 7);
        banner.setLayoutParams(layoutParams);
        initBanner();
        adapter.addHeaderView(view);

        listView.setAdapter(adapter);

        requestListData();
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

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    private void requestListData() {
        //status  0|预靠船舶，1|作业船舶，2|离港船舶
        String comingParams = "{\"shipStatus\":" + 0 + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("comingParams=" + comingParams);
        String workingParams = "{\"shipStatus\":" + 1 + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("workingParams=" + workingParams);
        Observable<ShipListEntity> comingObservable = RetrofitFactory.getInstance()
                .doGetShipList(comingParams);
        Observable<ShipListEntity> workingObservable = RetrofitFactory.getInstance()
                .doGetShipList(workingParams);

        Observable.zip(comingObservable, workingObservable, new BiFunction<ShipListEntity, ShipListEntity, List<ShipListEntity.DataBean>>() {
            @Override
            public List<ShipListEntity.DataBean> apply(ShipListEntity shipListEntity1, ShipListEntity shipListEntity2) throws Exception {
                String code1 = shipListEntity1.getCode();
                String code2 = shipListEntity2.getCode();
                if ("1".equals(code1) && "1".equals(code2)) {
                    List<ShipListEntity.DataBean> beanList1 = shipListEntity1.getData();
                    List<ShipListEntity.DataBean> beanList2 = shipListEntity2.getData();

                    List<ShipListEntity.DataBean> beanList = new ArrayList<>();
                    if (beanList2 != null && !beanList2.isEmpty()) {
                        dealList(Constant.TYPE_WORKING, beanList2);
                        beanList.addAll(beanList2);
                    } else {
                        ShipListEntity.DataBean bean = new ShipListEntity.DataBean();
                        bean.setShipType(Constant.TYPE_WORKING);
                        bean.setShipName("暂无数据");
                        beanList.add(bean);
                    }
                    if (beanList1 != null && !beanList1.isEmpty()) {
                        dealList(Constant.TYPE_COMING, beanList1);
                        beanList.addAll(beanList1);
                    } else {
                        ShipListEntity.DataBean bean = new ShipListEntity.DataBean();
                        bean.setShipType(Constant.TYPE_COMING);
                        bean.setShipName("暂无数据");
                        beanList.add(bean);
                    }
                    return beanList;
                } else if (!"1".equals(code1) && !"1".equals(code2)) {
                    List<ShipListEntity.DataBean> beanList = new ArrayList<>();
                    ShipListEntity.DataBean bean1 = new ShipListEntity.DataBean();
                    bean1.setShipType(Constant.TYPE_WORKING);
                    bean1.setShipName("数据加载失败，请稍后重试！");
                    beanList.add(bean1);

                    ShipListEntity.DataBean bean2 = new ShipListEntity.DataBean();
                    bean2.setShipType(Constant.TYPE_COMING);
                    bean2.setShipName("数据加载失败，请稍后重试！");
                    beanList.add(bean2);
                    return beanList;
                } else if ("1".equals(code1)) {//作业船舶获取失败
                    List<ShipListEntity.DataBean> beanList1 = shipListEntity1.getData();

                    List<ShipListEntity.DataBean> beanList = new ArrayList<>();
                    ShipListEntity.DataBean bean1 = new ShipListEntity.DataBean();
                    bean1.setShipType(Constant.TYPE_WORKING);
                    bean1.setShipName("数据加载失败，请稍后重试！");
                    beanList.add(bean1);

                    if (beanList1 != null && !beanList1.isEmpty()) {
                        dealList(Constant.TYPE_COMING, beanList1);
                        beanList.addAll(beanList1);
                    } else {
                        ShipListEntity.DataBean bean = new ShipListEntity.DataBean();
                        bean.setShipType(Constant.TYPE_COMING);
                        bean.setShipName("暂无数据");
                        beanList.add(bean);
                    }
                    return beanList;
                } else {//预靠船舶获取失败
                    List<ShipListEntity.DataBean> beanList = new ArrayList<>();

                    List<ShipListEntity.DataBean> beanList2 = shipListEntity2.getData();
                    if (beanList2 != null && !beanList2.isEmpty()) {
                        dealList(Constant.TYPE_WORKING, beanList2);
                        beanList.addAll(beanList2);
                    } else {
                        ShipListEntity.DataBean bean = new ShipListEntity.DataBean();
                        bean.setShipType(Constant.TYPE_WORKING);
                        bean.setShipName("暂无数据");
                        beanList.add(bean);
                    }

                    ShipListEntity.DataBean bean1 = new ShipListEntity.DataBean();
                    bean1.setShipType(Constant.TYPE_COMING);
                    bean1.setShipName("数据加载失败，请稍后重试！");
                    beanList.add(bean1);
                    return beanList;
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShipListEntity.DataBean>>() {
                    @Override
                    public void accept(List<ShipListEntity.DataBean> beanList) throws Exception {
                        errorCount = 0;
                        bindAdapter(beanList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (errorCount >= 3) {
                            if (refreshLayout != null) {
                                refreshLayout.setRefreshing(false);
                            }
                            listView.setVisibility(View.VISIBLE);
                            loadView.setVisibility(View.VISIBLE);
                            loadView.setLoadError(throwable.getMessage() + "");
                        } else {
                            errorCount++;
                            requestListData();
                        }
                    }
                });
    }

    private int errorCount;

    private void bindAdapter(List<ShipListEntity.DataBean> dataList) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
        adapter.loadMoreComplete();
        if (dataList != null && !dataList.isEmpty()) {
            loadView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter.setNewData(dataList);
        } else {
            adapter.loadMoreEnd();
            listView.setVisibility(View.VISIBLE);
            loadView.setVisibility(View.VISIBLE);
            loadView.setLoadType(LoadGroupView.TYPE_EMPTY);
        }
    }

    private void dealList(int type, List<ShipListEntity.DataBean> beanList) {
        if (beanList != null) {
            for (ShipListEntity.DataBean bean : beanList) {
                bean.setShipType(type);
            }
        }
    }

    @Override
    public void onRefresh() {
        requestListData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String tag) {
        if ("BeginShip".equals(tag) || "FinishedShip".equals(tag)) {
            if (refreshLayout != null) {
                refreshLayout.setRefreshing(true);
            }
            requestListData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShipListEntity shipListEntity) {
        ToastUtils.show(activity, shipListEntity.getMsg() + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
