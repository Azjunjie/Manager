package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.ShipUnloaderDetailProgressListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetUnloaderUnshipDetailListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 卸船机卸货明细列表
 */
public class ShipUnloaderDetailProgressActivity extends BaseActivity {

    @BindView(R.id.btn_ship_info)
    TextView btnShipInfo;

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private String taskId;
    private String unloaderId;
    private String startTime;
    private String endTime;
    private ShipUnloaderDetailProgressListAdapter adapter;

    public static Intent getIntent(Context context, String taskId, String shipName
            , String unloaderName, String unloaderId, String startTime, String endTime) {
        Intent intent = new Intent(context, ShipUnloaderDetailProgressActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("shipName", shipName);
        intent.putExtra("unloaderName", unloaderName);
        intent.putExtra("unloaderId", unloaderId);
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_unloader_detail_progress;
    }

    @Override
    protected void initView() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });

        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        String shipName = intent.getStringExtra("shipName");
        btnShipInfo.setText(shipName + "");
        String unloaderName = intent.getStringExtra("unloaderName");
        ToolBarUtil.init(activity, unloaderName + "");
        unloaderId = intent.getStringExtra("unloaderId");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");

        adapter = new ShipUnloaderDetailProgressListAdapter(R.layout.layout_ship_unloader_detail_progres_list_item, null);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<GetUnloaderUnshipDetailListEntity.DataBean> list = adapter.getData();
                GetUnloaderUnshipDetailListEntity.DataBean bean = list.get(position);
                startActivity(ShipCabinDetailActivity.getIntent(activity, taskId, bean.getCabinNo()));
            }
        });
        listView.setAdapter(adapter);

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        requestData();
    }

    private void requestData() {
        String params = "{\"taskId\":" + taskId + ",\"unloaderId\":\"" + unloaderId
                + "\",\"startTime\":\"" + startTime
                + "\",\"endTime\":\"" + endTime
                + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("doGetUnloaderUnshipInfo json=" + params);

        RetrofitFactory.getInstance()
                .doGetUnloaderUnshipDetailList(params)
                .compose(RxSchedulers.<GetUnloaderUnshipDetailListEntity>compose())
                .subscribe(new BaseObserver<GetUnloaderUnshipDetailListEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(GetUnloaderUnshipDetailListEntity entity) {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                        loadView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        List<GetUnloaderUnshipDetailListEntity.DataBean> list = entity.getData();
                        adapter.setNewData(list);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                        loadView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        loadView.setLoadError(msg + "");
                    }
                });
    }

    @OnClick({R.id.btn_ship_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ship_info:
                startActivity(ShipBaseInfoActivity.getIntent(activity, taskId));
                break;
        }
    }
}
