package com.aitewei.manager.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipCabinListActivity;
import com.aitewei.manager.adapter.ShipListAdapter;
import com.aitewei.manager.base.BaseFragment;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.ShipListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * "作业船舶"、"预靠船舶"列表
 */
public class ShipListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list_view)
    RecyclerView listView;

    private int type;
    private ShipListAdapter adapter;

    public static ShipListFragment newInstance(int type) {
        ShipListFragment shipListFragment = new ShipListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        shipListFragment.setArguments(bundle);
        return shipListFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ship_list;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        refreshLayout.setOnRefreshListener(this);

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        type = getArguments().getInt("type");

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        adapter = new ShipListAdapter(R.layout.layout_ship_list_item, null);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ShipListEntity.DataBean> dataList = adapter.getData();
                ShipListEntity.DataBean bean = dataList.get(position);
                startActivity(ShipCabinListActivity.getIntent(activity, type, bean.getId(), bean.getShipName()));
            }
        });
        listView.setAdapter(adapter);

        requestListData();
    }

    private void requestListData() {
        int status = 0;//0|预靠船舶，1|作业船舶，2|离港船舶
        if (type == Constant.TYPE_COMING) {
            status = 0;
        } else if (type == Constant.TYPE_WORKING) {
            status = 1;
        } else if (type == Constant.TYPE_FINISH) {
            status = 2;
        }
        String params = "{\"shipStatus\":" + status + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("params=" + params);
        RetrofitFactory.getInstance()
                .doGetShipList(params)
                .compose(RxSchedulers.<ShipListEntity>compose())
                .subscribe(new BaseObserver<ShipListEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(ShipListEntity entity) {
                        errorCount = 0;
                        List<ShipListEntity.DataBean> beanList = entity.getData();
                        bindAdapter(beanList);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        if (errorCount >= 3) {
                            if (refreshLayout != null) {
                                refreshLayout.setRefreshing(false);
                            }
                            listView.setVisibility(View.GONE);
                            loadView.setVisibility(View.VISIBLE);
                            loadView.setLoadError(msg + "");
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
            listView.setVisibility(View.GONE);
            loadView.setVisibility(View.VISIBLE);
            loadView.setLoadType(LoadGroupView.TYPE_EMPTY);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
