package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.ShipBaseInfoEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;

import butterknife.BindView;

/**
 * 船舶基本信息
 */
public class ShipBaseInfoActivity extends BaseActivity {

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.content_view)
    ScrollView contentView;

    @BindView(R.id.tv_berthingTime)
    TextView tvBerthingTime;
    @BindView(R.id.tv_departureTime)
    TextView tvDepartureTime;
    @BindView(R.id.tv_beginTime)
    TextView tvBeginTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.tv_shipName)
    TextView tvShipName;
    @BindView(R.id.tv_shipEname)
    TextView tvShipEname;
    @BindView(R.id.tv_imoNo)
    TextView tvImoNo;
    @BindView(R.id.tv_buildDate)
    TextView tvBuildDate;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(R.id.tv_breadth)
    TextView tvBreadth;
    @BindView(R.id.tv_depth)
    TextView tvDepth;
    @BindView(R.id.tv_cabinNum)
    TextView tvCabinNum;
    @BindView(R.id.tv_hatch)
    TextView tvHatch;
    @BindView(R.id.tv_cable)
    TextView tvCable;
    @BindView(R.id.tv_freeboardHeight)
    TextView tvFreeboardHeight;
    @BindView(R.id.tv_draft)
    TextView tvDraft;
    @BindView(R.id.tv_load)
    TextView tvLoad;
    @BindView(R.id.tv_cabinType)
    TextView tvCabinType;

    private String taskId;

    public static Intent getIntent(Context context, String taskId) {
        Intent intent = new Intent(context, ShipBaseInfoActivity.class);
        intent.putExtra("taskId", taskId);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_base_info;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "船舶基本信息");
    }

    @Override
    protected void initData() {
        taskId = getIntent().getStringExtra("taskId");

        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        requestShipInfoData();
    }

    private void requestShipInfoData() {
        String params = "{\"taskId\":\"" + taskId + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e(params + "");
        RetrofitFactory.getInstance()
                .doGetShipDetail(params)
                .compose(RxSchedulers.<ShipBaseInfoEntity>compose())
                .subscribe(new BaseObserver<ShipBaseInfoEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(ShipBaseInfoEntity entity) {
                        ShipBaseInfoEntity.DataBean dataBean = entity.getData();
                        bindInfo(dataBean);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        contentView.setVisibility(View.GONE);
                        loadView.setVisibility(View.VISIBLE);
                        loadView.setLoadError(msg + "");
                    }
                });
    }

    private void bindInfo(ShipBaseInfoEntity.DataBean dataBean) {
        loadView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);

        tvBerthingTime.setText(dataBean.getBerthingTime() + "");
        tvDepartureTime.setText(dataBean.getDepartureTime() + "");
        tvBeginTime.setText(dataBean.getBeginTime() + "");
        tvEndTime.setText(dataBean.getEndTime() + "");
        tvShipName.setText(dataBean.getShipName() + "");
        tvShipEname.setText(dataBean.getShipEname() + "");
        tvImoNo.setText(dataBean.getImoNo() + "");
        tvBuildDate.setText(dataBean.getBuildDate() + "");
        tvLength.setText(dataBean.getLength() + "");
        tvBreadth.setText(dataBean.getBreadth() + "");
        tvDepth.setText(dataBean.getDepth() + "");
        tvCabinNum.setText(dataBean.getCabinNum() + "");
        tvHatch.setText(dataBean.getHatch() + "");
        tvCable.setText(dataBean.getCable() + "");
        tvFreeboardHeight.setText(dataBean.getFreeboardHeight() + "");
        tvDraft.setText(dataBean.getDraft() + "");
        tvLoad.setText(dataBean.getLoad() + "");
        tvCabinType.setText(dataBean.getCabintype() + "");
    }

}
