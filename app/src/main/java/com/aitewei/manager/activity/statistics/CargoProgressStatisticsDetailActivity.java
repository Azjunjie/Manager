package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipCargoDetailActivity;
import com.aitewei.manager.adapter.UnloaderStatisticsListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.CarbinInfoStatisticsEntity;
import com.aitewei.manager.entity.UnloaderInfoStatisticsEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 货物进度统计-单个货物明细
 */
public class CargoProgressStatisticsDetailActivity extends BaseActivity {

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.tv_cabinNo)
    TextView tvCabinNo;
    @BindView(R.id.tv_cargoType)
    TextView tvCargoType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_remainder)
    TextView tvRemainder;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_finished)
    TextView tvFinished;
    @BindView(R.id.tv_clearance)
    TextView tvClearance;

    @BindView(R.id.btn_refresh)
    FrameLayout btnRefresh;

    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.content_view)
    LinearLayout contentView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private UnloaderStatisticsListAdapter adapter;

    private String taskId;
    private String cargoId;
    private String cabinNo;

    public static Intent getIntent(Context context, String taskId, String cabinNo, String cargoId, String cargoName) {
        Intent intent = new Intent(context, CargoProgressStatisticsDetailActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("cabinNo", cabinNo);
        intent.putExtra("cargoId", cargoId);
        intent.putExtra("cargoName", cargoName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cargo_progress_statistics_detail;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(this, "货物进度详情");

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
        taskId = getIntent().getStringExtra("taskId");
        cabinNo = getIntent().getStringExtra("cabinNo");
        cargoId = getIntent().getStringExtra("cargoId");
        String cargoName = getIntent().getStringExtra("cargoName");
        tvCargoType.setText(cargoName + "");

        adapter = new UnloaderStatisticsListAdapter(R.layout.layout_cabin_progress_list_item, null);
        listView.setAdapter(adapter);

        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        requestData();
    }

    private void requestData() {
        String params1 = "{\"taskId\":" + taskId + ",\"userId\":\"" + User.newInstance().getUserId() +
                "\",\"cabinNo\":\"" + cabinNo + "\",\"cargoId\":" + cargoId + "}";
        LogUtil.e("json=" + params1);
        RetrofitFactory.getInstance()
                .doCabinInfoStatistics(params1)
                .compose(RxSchedulers.<CarbinInfoStatisticsEntity>compose())
                .subscribe(new BaseObserver<CarbinInfoStatisticsEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(CarbinInfoStatisticsEntity entity) {
                        refreshLayout.setEnabled(true);
                        btnRefresh.setClickable(true);
                        contentView.setVisibility(View.VISIBLE);
                        loadView.setVisibility(View.GONE);
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                        bindDetail(entity.getData().get(0));
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        btnRefresh.setClickable(true);
                        refreshLayout.setEnabled(true);
                        contentView.setVisibility(View.GONE);
                        loadView.setVisibility(View.VISIBLE);
                        loadView.setLoadError(msg + "");
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });

        //卸船机的数据
        String params = "{\"taskId\":" + taskId + ",\"cargoId\":\"" + cargoId
                + "\",\"cabinNo\":\"" + cabinNo
                + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("doGetUnloaderUnshipInfo json=" + params);
        RetrofitFactory.getInstance()
                .doUnloaderInfoStatistics(params)
                .compose(RxSchedulers.<UnloaderInfoStatisticsEntity>compose())
                .subscribe(new BaseObserver<UnloaderInfoStatisticsEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(UnloaderInfoStatisticsEntity entity) {
                        adapter.setNewData(entity.getData());
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                    }
                });
    }


    private void bindDetail(CarbinInfoStatisticsEntity.DataBean data) {
        tvCabinNo.setText(data.getCabinNo() + "#舱");
        tvCargoType.setText(data.getCargoName() + "");
        tvTotal.setText(data.getTotal() + "");
        tvRemainder.setText(data.getRemainder() + "");
        tvFinished.setText(data.getFinished() + "");
        tvClearance.setText(data.getClearance() + "");
        //0|卸货;1|清舱;2|完成
        String status = data.getStatus() + "";
        if ("0".equals(status)) {
            tvStatus.setText("卸货");
        } else if ("1".equals(status)) {
            tvStatus.setText("清舱");
        } else if ("2".equals(status)) {
            tvStatus.setText("完成");
        } else {
            tvStatus.setText("未开始");
        }
    }

    @OnClick(R.id.tv_cargoType)
    public void onViewClicked() {
        startActivity(ShipCargoDetailActivity.getIntent(activity, taskId, cargoId));
    }


}
