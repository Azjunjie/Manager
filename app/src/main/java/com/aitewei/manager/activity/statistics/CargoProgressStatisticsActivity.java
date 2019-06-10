package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipBaseInfoActivity;
import com.aitewei.manager.adapter.CargoProgressStatisticsListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.CargoInfoStatisticsEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.aitewei.manager.view.NoscrollListView;
import com.aitewei.manager.view.SyncHorizontalScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 船舶货物进度统计页
 */
public class CargoProgressStatisticsActivity extends BaseActivity {

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.content_view)
    ScrollView contentView;

    @BindView(R.id.btn_ship_info)
    TextView btnShipInfo;

    @BindView(R.id.lv_left)
    NoscrollListView mLeft;
    @BindView(R.id.lv_progress_data)
    NoscrollListView mLvProgressData;
    @BindView(R.id.lv_data)
    NoscrollListView mData;
    @BindView(R.id.header_horizontal)
    SyncHorizontalScrollView mHeaderHorizontal;
    @BindView(R.id.data_horizontal)
    SyncHorizontalScrollView mDataHorizontal;

    @BindView(R.id.btn_refresh)
    FrameLayout btnRefresh;

    @BindView(R.id.ll_progress_contianer)
    LinearLayout llProgressContianer;

    private CargoProgressStatisticsListAdapter leftAdapter;
    private CargoProgressStatisticsListAdapter rightAdapter;

    private List<CargoInfoStatisticsEntity.DataBean> list;

    private String taskId;
    private int showType;
    private DecimalFormat decimalFormat;

    public static Intent getIntent(Context context, int type, String taskId, String shipName) {
        Intent intent = new Intent(context, CargoProgressStatisticsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("taskId", taskId);
        intent.putExtra("shipName", shipName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cargo_progress_statistics;
    }

    @Override
    protected void initView() {
        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);
    }

    @Override
    protected void initData() {
        decimalFormat = new DecimalFormat("0.0");
        showType = getIntent().getIntExtra("type", 0);
        if (showType == Constant.TYPE_PROGRESS) {
            llProgressContianer.setVisibility(View.VISIBLE);
            mLvProgressData.setVisibility(View.VISIBLE);
            mHeaderHorizontal.setVisibility(View.GONE);
            mDataHorizontal.setVisibility(View.GONE);
            ToolBarUtil.init(activity, "船舶货物进度统计");
        } else {
            llProgressContianer.setVisibility(View.GONE);
            mLvProgressData.setVisibility(View.GONE);
            mHeaderHorizontal.setVisibility(View.VISIBLE);
            mDataHorizontal.setVisibility(View.VISIBLE);
            ToolBarUtil.init(activity, "船舶货物效率统计");
        }

        taskId = getIntent().getStringExtra("taskId");
        String shipName = getIntent().getStringExtra("shipName");
        btnShipInfo.setText(shipName + "");

        btnRefresh.setClickable(false);
        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        initAdapter();

        requestData();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        leftAdapter = new CargoProgressStatisticsListAdapter(this, CargoProgressStatisticsListAdapter.LEFT
                , showType, list, R.layout.item_left);
        leftAdapter.setOnItemChildClick(new CargoProgressStatisticsListAdapter.OnItemChildClick() {
            @Override
            public void onItemChildClick(int position) {
                CargoInfoStatisticsEntity.DataBean bean = list.get(position);

                String cargoName = bean.getCargoName();
                if (!"合计".equals(cargoName)) {
                    startActivity(CabinStatisticsActivity.getIntent(activity, showType,
                            taskId, bean.getCargoId(), cargoName));
                }
            }
        });
        mLeft.setAdapter(leftAdapter);

        if (showType == Constant.TYPE_PROGRESS) {
            rightAdapter = new CargoProgressStatisticsListAdapter(this, CargoProgressStatisticsListAdapter.RIGHT
                    , showType, list, R.layout.item_cargo_progress_statistics_right);
            mLvProgressData.setAdapter(rightAdapter);
        } else {
            rightAdapter = new CargoProgressStatisticsListAdapter(this, CargoProgressStatisticsListAdapter.RIGHT
                    , showType, list, R.layout.item_cargo_statistics_right);
            mData.setAdapter(rightAdapter);
        }
    }

    private void requestData() {
        String params = "{\"taskId\":" + taskId + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("doGetCargoUnshipInfo json=" + params);
        RetrofitFactory.getInstance()
                .doCargoInfoStatistics(params)
                .compose(RxSchedulers.<CargoInfoStatisticsEntity>compose())
                .subscribe(new BaseObserver<CargoInfoStatisticsEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(CargoInfoStatisticsEntity entity) {
                        dismissLoadingPopup();
                        if (btnRefresh == null) {
                            return;
                        }
                        btnRefresh.setClickable(true);
                        loadView.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);

                        List<CargoInfoStatisticsEntity.DataBean> beanList = entity.getData();
                        list.clear();
                        list.addAll(beanList);

                        CargoInfoStatisticsEntity.DataBean dataBean = new CargoInfoStatisticsEntity.DataBean();
                        dataBean.setCargoName("合计");
                        for (CargoInfoStatisticsEntity.DataBean bean : beanList) {
                            dataBean.setTotal(dataBean.getTotal() + bean.getTotal());
                            dataBean.setFinishedBeforeClearance(dataBean.getFinishedBeforeClearance() + bean.getFinishedBeforeClearance());
                            dataBean.setFinishedUsedTimeBeforeClearance(dataBean.getFinishedUsedTimeBeforeClearance() + bean.getFinishedUsedTimeBeforeClearance());
                            dataBean.setClearance(dataBean.getClearance() + bean.getClearance());
                            dataBean.setClearanceUsedTime(dataBean.getClearanceUsedTime() + bean.getClearanceUsedTime());
                            dataBean.setFinished(dataBean.getFinished() + bean.getFinished());
                            dataBean.setFinishedUsedTime(dataBean.getFinishedUsedTime() + bean.getFinishedUsedTime());
                            dataBean.setRemainder(dataBean.getRemainder() + bean.getRemainder());
                        }
                        dataBean.setTotal(Double.valueOf(decimalFormat.format(dataBean.getTotal())));
                        dataBean.setFinishedBeforeClearance(Double.valueOf(decimalFormat.format(dataBean.getFinishedBeforeClearance())));
                        dataBean.setFinishedUsedTimeBeforeClearance(Double.valueOf(decimalFormat.format(dataBean.getFinishedUsedTimeBeforeClearance())));
                        dataBean.setClearance(Double.valueOf(decimalFormat.format(dataBean.getClearance())));
                        dataBean.setClearanceUsedTime(Double.valueOf(decimalFormat.format(dataBean.getClearanceUsedTime())));
                        dataBean.setFinished(Double.valueOf(decimalFormat.format(dataBean.getFinished())));
                        dataBean.setFinishedUsedTime(Double.valueOf(decimalFormat.format(dataBean.getFinishedUsedTime())));
                        dataBean.setRemainder(Double.valueOf(decimalFormat.format(dataBean.getRemainder())));
                        dataBean.setClearTime("--");

                        double beforeUseTime = dataBean.getFinishedUsedTimeBeforeClearance();
                        if (beforeUseTime == 0) {
                            dataBean.setFinishedEfficiencyBeforeClearance(0);
                        } else {
                            dataBean.setFinishedEfficiencyBeforeClearance(Double.valueOf(
                                    decimalFormat.format(dataBean.getFinishedBeforeClearance() / beforeUseTime)));
                        }

                        double clearanceUseTime = dataBean.getClearanceUsedTime();
                        if (clearanceUseTime == 0) {
                            dataBean.setClearanceEfficiency(0);
                        } else {
                            dataBean.setClearanceEfficiency(Double.valueOf(
                                    decimalFormat.format(dataBean.getClearance() / clearanceUseTime)));
                        }

                        double finishedUseTime = dataBean.getFinishedUsedTime();
                        if (finishedUseTime == 0) {
                            dataBean.setFinishedEfficiency(0);
                        } else {
                            dataBean.setFinishedEfficiency(Double.valueOf(
                                    decimalFormat.format(dataBean.getFinished() / finishedUseTime)));
                        }

                        list.add(dataBean);
                        leftAdapter.notifyDataSetChanged();
                        rightAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        dismissLoadingPopup();
                        if (btnRefresh != null) {
                            btnRefresh.setClickable(true);
                            loadView.setVisibility(View.VISIBLE);
                            contentView.setVisibility(View.GONE);
                            loadView.setLoadError(msg + "");
                        }
                    }
                });
    }

    @OnClick({R.id.btn_refresh, R.id.btn_ship_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh:
                btnRefresh.setClickable(false);
                showLoadingPopup();
                requestData();
                break;
            case R.id.btn_ship_info:
                startActivity(ShipBaseInfoActivity.getIntent(activity, taskId));
                break;
        }
    }

}
