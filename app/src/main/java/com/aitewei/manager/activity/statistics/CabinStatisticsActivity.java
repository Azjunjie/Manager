package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipBaseInfoActivity;
import com.aitewei.manager.activity.ship.ShipCargoDetailActivity;
import com.aitewei.manager.adapter.CabinProgressStatisticsListAdapter;
import com.aitewei.manager.adapter.CargoProgressStatisticsListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.CarbinInfoStatisticsEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.aitewei.manager.view.NoscrollListView;
import com.aitewei.manager.view.SyncHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 舱口统计
 */
public class CabinStatisticsActivity extends BaseActivity {

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.content_view)
    ScrollView contentView;

    @BindView(R.id.btn_ship_info)
    TextView btnShipInfo;

    @BindView(R.id.lv_left)
    NoscrollListView mLeft;
    @BindView(R.id.lv_data)
    NoscrollListView mData;
    @BindView(R.id.header_horizontal)
    SyncHorizontalScrollView mHeaderHorizontal;
    @BindView(R.id.data_horizontal)
    SyncHorizontalScrollView mDataHorizontal;
    @BindView(R.id.ll_progress_contianer)
    LinearLayout llProgressContianer;
    @BindView(R.id.lv_progress_data)
    NoscrollListView mLvProgressData;

    @BindView(R.id.btn_refresh)
    FrameLayout btnRefresh;

    private CabinProgressStatisticsListAdapter leftAdapter;
    private CabinProgressStatisticsListAdapter rightAdapter;

    private List<CarbinInfoStatisticsEntity.DataBean> list;

    private String taskId;
    private String cargoId;
    private int showType;

    public static Intent getIntent(Context context, int type, String taskId, String shipName) {
        Intent intent = new Intent(context, CabinStatisticsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("taskId", taskId);
        intent.putExtra("shipName", shipName);
        return intent;
    }

    public static Intent getIntent(Context context, int type, String taskId, String cargoId, String cargoName) {
        Intent intent = new Intent(context, CabinStatisticsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("taskId", taskId);
        intent.putExtra("cargoId", cargoId);
        intent.putExtra("cargoName", cargoName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cabin_statistics;
    }

    @Override
    protected void initView() {
        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);
    }

    @Override
    protected void initData() {
        showType = getIntent().getIntExtra("type", 0);
        if (showType == Constant.TYPE_PROGRESS) {
            llProgressContianer.setVisibility(View.VISIBLE);
            mLvProgressData.setVisibility(View.VISIBLE);
            mHeaderHorizontal.setVisibility(View.GONE);
            mDataHorizontal.setVisibility(View.GONE);
        } else {
            llProgressContianer.setVisibility(View.GONE);
            mLvProgressData.setVisibility(View.GONE);
            mHeaderHorizontal.setVisibility(View.VISIBLE);
            mDataHorizontal.setVisibility(View.VISIBLE);
        }

        taskId = getIntent().getStringExtra("taskId");
        cargoId = getIntent().getStringExtra("cargoId");
        if (TextUtils.isEmpty(cargoId)) {
            String shipName = getIntent().getStringExtra("shipName");
            btnShipInfo.setText(shipName + "");
            if (showType == Constant.TYPE_PROGRESS) {
                ToolBarUtil.init(activity, "船舶舱口进度统计");
            }else {
                ToolBarUtil.init(activity, "船舶舱口效率统计");
            }
        } else {
            String cargoName = getIntent().getStringExtra("cargoName");
            btnShipInfo.setText(cargoName + "");
            if (showType == Constant.TYPE_PROGRESS) {
                ToolBarUtil.init(activity, "船舶货物进度统计");
            }else {
                ToolBarUtil.init(activity, "船舶货物效率统计");
            }
        }

        btnRefresh.setClickable(false);
        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        initAdapter();

        requestData();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        leftAdapter = new CabinProgressStatisticsListAdapter(this, CabinProgressStatisticsListAdapter.LEFT
                , showType, list, R.layout.item_left);
        leftAdapter.setOnItemChildClick(new CabinProgressStatisticsListAdapter.OnItemChildClick() {
            @Override
            public void onItemChildClick(int position) {
                CarbinInfoStatisticsEntity.DataBean bean = list.get(position);
                if ("合计".equals(bean.getCabinNo())) {
                    return;
                }
                startActivity(CargoProgressStatisticsDetailActivity.getIntent(activity,
                        taskId, bean.getCabinNo(), bean.getCargoId(), bean.getCargoName()));
            }
        });
        mLeft.setAdapter(leftAdapter);

        if (showType == Constant.TYPE_PROGRESS) {
            rightAdapter = new CabinProgressStatisticsListAdapter(this, CargoProgressStatisticsListAdapter.RIGHT
                    , showType, list, R.layout.item_cargo_progress_statistics_right);
            mLvProgressData.setAdapter(rightAdapter);
        } else {
            rightAdapter = new CabinProgressStatisticsListAdapter(this, CabinProgressStatisticsListAdapter.RIGHT
                    , showType, list, R.layout.item_cargo_statistics_right);
            mData.setAdapter(rightAdapter);
        }
    }

    private void requestData() {
        String params = "{\"taskId\":" + taskId + ",\"userId\":\"" + User.newInstance().getUserId() +
                "\",\"cargoId\":" + cargoId + "}";

        LogUtil.e("doGetCargoUnshipInfo json=" + params);
        RetrofitFactory.getInstance()
                .doCabinInfoStatistics(params)
                .compose(RxSchedulers.<CarbinInfoStatisticsEntity>compose())
                .subscribe(new BaseObserver<CarbinInfoStatisticsEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(CarbinInfoStatisticsEntity entity) {
                        loadView.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);

                        list.clear();
                        list.addAll(entity.getData());
                        if (!TextUtils.isEmpty(cargoId)) {
                            CarbinInfoStatisticsEntity.DataBean bean = new CarbinInfoStatisticsEntity.DataBean();
                            for (CarbinInfoStatisticsEntity.DataBean dataBean : list) {
                                bean.setTotal(bean.getTotal() + dataBean.getTotal());
                                bean.setFinished(bean.getFinished() + dataBean.getFinished());
                                bean.setFinishedUsedTime(bean.getFinishedUsedTime() + dataBean.getFinishedUsedTime());
                                bean.setRemainder(bean.getRemainder() + dataBean.getRemainder());
                                bean.setClearance(bean.getClearance() + dataBean.getClearance());
                                bean.setClearanceUsedTime(bean.getClearanceUsedTime() + dataBean.getClearanceUsedTime());
                                bean.setFinishedEfficiency(bean.getFinishedEfficiency() + dataBean.getFinishedEfficiency());
                                bean.setClearanceEfficiency(bean.getClearanceEfficiency() + dataBean.getClearanceEfficiency());
                            }
                            bean.setClearTime("--");
                            bean.setCabinNo("合计");
                            list.add(bean);
                        }
                        leftAdapter.notifyDataSetChanged();
                        rightAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        loadView.setVisibility(View.VISIBLE);
                        contentView.setVisibility(View.GONE);
                        loadView.setLoadError(msg + "");
                    }
                });
    }

    @OnClick(R.id.btn_ship_info)
    public void onViewClicked() {
        if (TextUtils.isEmpty(cargoId)) {
            startActivity(ShipBaseInfoActivity.getIntent(activity, taskId));
        } else {
            startActivity(ShipCargoDetailActivity.getIntent(activity, taskId, cargoId));
        }
    }

}
