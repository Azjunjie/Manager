package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.ShipGoodsDetailEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;

import butterknife.BindView;

/**
 * 船舱货物信息
 */
public class ShipGoodsDetailActivity extends BaseActivity {
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.content_view)
    ScrollView contentView;

    @BindView(R.id.tv_cargoType)
    TextView tvCargoType;
    @BindView(R.id.tv_cargoCategory)
    TextView tvCargoCategory;
    @BindView(R.id.tv_loadingPort)
    TextView tvLoadingPort;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    @BindView(R.id.tv_moisture)
    TextView tvMoisture;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_stowage)
    TextView tvStowage;
    @BindView(R.id.tv_warehouse)
    TextView tvWarehouse;

    private String taskId;
    private int cabinNo;

    public static Intent getIntent(Context context, String taskId, int cabinNo) {
        Intent intent = new Intent(context, ShipGoodsDetailActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("cabinNo", cabinNo);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_goods;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "船舶货物基本信息");
    }

    @Override
    protected void initData() {
        taskId = getIntent().getStringExtra("taskId");
        cabinNo = getIntent().getIntExtra("cabinNo", 0);

        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        requestDetailData();
    }

    private void requestDetailData() {
        String params = "{\"taskId\":\"" + taskId + "\",\"userId\":\"" + User.newInstance().getUserId() + "\""
                + ",\"cabinNo\":" + cabinNo + "}";
        LogUtil.e(params + "");
        RetrofitFactory.getInstance()
                .doGetCargoDetail(params)
                .compose(RxSchedulers.<ShipGoodsDetailEntity>compose())
                .subscribe(new BaseObserver<ShipGoodsDetailEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(ShipGoodsDetailEntity entity) {
                        ShipGoodsDetailEntity.DataBean detailBean = entity.getData();
                        bindDetail(detailBean);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        contentView.setVisibility(View.GONE);
                        loadView.setVisibility(View.VISIBLE);
                        loadView.setLoadError(msg + "");
                    }
                });
    }

    private void bindDetail(ShipGoodsDetailEntity.DataBean dataBean) {
        loadView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);

        tvCargoType.setText(dataBean.getCargoType() + "");
        tvCargoCategory.setText(dataBean.getCargoCategory() + "");
        tvLoadingPort.setText(dataBean.getLoadingPort() + "");
        tvQuality.setText(dataBean.getQuality() + "");
        tvMoisture.setText(dataBean.getMoisture() + "");
        tvOwner.setText(dataBean.getOwner() + "");
        tvStowage.setText(dataBean.getStowage() + "");
        tvWarehouse.setText(dataBean.getWarehouse() + "");
    }
}
