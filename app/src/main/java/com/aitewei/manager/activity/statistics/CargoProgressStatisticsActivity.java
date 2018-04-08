package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipBaseInfoActivity;
import com.aitewei.manager.adapter.CargoProgressStatisticsListAdapter;
import com.aitewei.manager.base.BaseActivity;
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
    @BindView(R.id.lv_data)
    NoscrollListView mData;
    @BindView(R.id.header_horizontal)
    SyncHorizontalScrollView mHeaderHorizontal;
    @BindView(R.id.data_horizontal)
    SyncHorizontalScrollView mDataHorizontal;

    @BindView(R.id.btn_refresh)
    FrameLayout btnRefresh;

    private CargoProgressStatisticsListAdapter leftAdapter;
    private CargoProgressStatisticsListAdapter rightAdapter;

    private List<CargoInfoStatisticsEntity.DataBean> list;

    private String taskId;
    private int showType;

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
        ToolBarUtil.init(activity, "船舶货物进度统计");

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);
    }

    @Override
    protected void initData() {
        showType = getIntent().getIntExtra("type", 0);

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

                startActivity(CabinStatisticsActivity.getIntent(activity, showType,
                        taskId, bean.getCargoId(), bean.getCargoName()));
            }
        });
        mLeft.setAdapter(leftAdapter);

        rightAdapter = new CargoProgressStatisticsListAdapter(this, CargoProgressStatisticsListAdapter.RIGHT
                , showType, list, R.layout.item_cargo_statistics_right);
        mData.setAdapter(rightAdapter);
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
                        loadView.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);

                        list.clear();
                        list.addAll(entity.getData());
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
        startActivity(ShipBaseInfoActivity.getIntent(activity, taskId));
    }

}
