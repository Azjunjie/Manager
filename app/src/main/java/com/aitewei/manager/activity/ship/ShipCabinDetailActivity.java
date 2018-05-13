package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.UnloadListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.common.PermissionsCode;
import com.aitewei.manager.common.Popup;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetUploaderDetailEntity;
import com.aitewei.manager.entity.ShipCabinDetailEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShipCabinDetailActivity extends BaseActivity {

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
    @BindView(R.id.btn_complete)
    Button btnComplete;

    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.content_view)
    LinearLayout contentView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private String taskId;
    private int cabinNo;
    private UnloadListAdapter adapter;

    private String status;

    public static Intent getIntent(Context context, String taskId, int cabinNo) {
        Intent intent = new Intent(context, ShipCabinDetailActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("cabinNo", cabinNo);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_cabin_detail;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸货进度");
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestCabinDetailData();
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        cabinNo = intent.getIntExtra("cabinNo", 0);

        initAdapter();

        refreshLayout.setRefreshing(false);
        contentView.setVisibility(View.GONE);
        loadView.setVisibility(View.VISIBLE);
        loadView.setLoadType(LoadGroupView.TYPE_LOADING);
        btnRefresh.setClickable(false);

        handler.sendEmptyMessageDelayed(1, 1000 * 60 * 3);
    }

    private void initAdapter() {
        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setHasFixedSize(true);

        adapter = new UnloadListAdapter(R.layout.layout_unload_list_item, null);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCabinDetailData();
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeMessages(1);
        }
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.e("refresh data=====");
            requestCabinDetailData();
            handler.sendEmptyMessageDelayed(1, 1000 * 60 * 3);
        }
    };

    private void requestCabinDetailData() {
        String params = "{\"taskId\":\"" + taskId + "\",\"cabinNo\":\"" + cabinNo + "\""
                + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("params=" + params);
        RetrofitFactory.getInstance()
                .doGetCabinDetail(params)
                .compose(RxSchedulers.<ShipCabinDetailEntity>compose())
                .subscribe(new BaseObserver<ShipCabinDetailEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(ShipCabinDetailEntity entity) {
                        dismissLoadingPopup();
                        refreshLayout.setEnabled(true);
                        btnRefresh.setClickable(true);
                        contentView.setVisibility(View.VISIBLE);
                        loadView.setVisibility(View.GONE);
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                        bindDetail(entity.getData());
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        dismissLoadingPopup();
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

        RetrofitFactory.getInstance()
                .doGetUnloaderDetail(params)
                .compose(RxSchedulers.<GetUploaderDetailEntity>compose())
                .subscribe(new BaseObserver<GetUploaderDetailEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(GetUploaderDetailEntity entity) {
                        List<GetUploaderDetailEntity.UnloaderDetailBean> list = entity.getData();
                        adapter.setNewData(list);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {

                    }
                });
    }

    private void bindDetail(ShipCabinDetailEntity.DataBean data) {
        tvCabinNo.setText(data.getCabinNo() + "");
        tvCargoType.setText(data.getCargoName() + "");
        tvTotal.setText(data.getTotal() + "");
        tvRemainder.setText(data.getRemainder() + "");
        tvFinished.setText(data.getFinished() + "");
        tvClearance.setText(data.getClearance() + "");
        //0|卸货;1|清舱;2|完成
        status = data.getStatus() + "";
        if (PermissionsCode.isHasPermission(PermissionsCode.clearStatus)) {
            btnComplete.setVisibility(View.VISIBLE);
            if ("0".equals(status)) {
                tvStatus.setText("卸货");
                btnComplete.setText("清舱");
            } else if ("1".equals(status)) {
                tvStatus.setText("清舱");
                btnComplete.setText("完成");
            } else if ("2".equals(status)) {
                tvStatus.setText("完成");
                btnComplete.setVisibility(View.GONE);
            } else {
                tvStatus.setText("未开始");
                btnComplete.setText("卸货");
            }
        } else {
            btnComplete.setVisibility(View.GONE);
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
    }

    @OnClick({R.id.tv_cargoType, R.id.btn_refresh, R.id.btn_complete})
    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.tv_cargoType://种类
                startActivity(ShipCargoDetailActivity.getIntent(activity, taskId, cabinNo));
                break;
            case R.id.btn_refresh:
                btnRefresh.setClickable(false);
                showLoadingPopup();
                requestCabinDetailData();
                break;
            case R.id.btn_complete:
                if ("0".equals(status)) {
                    showSetCabinStatusPopup(1);
                } else if ("1".equals(status)) {
                    showSetCabinStatusPopup(2);
                } else if ("2".equals(status)) {
                } else {
                    showSetCabinStatusPopup(0);
                }
                break;
        }
    }

    private Popup setCabinStatusPopup;
    private TextView tvCabinTitle;
    private TextView btnCabinConfirm;

    /**
     * 设置船舱状态的确认弹窗
     *
     * @param status 0|卸货;1|清舱;2|完成
     */
    private void showSetCabinStatusPopup(final int status) {
        if (setCabinStatusPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm_cancle, null);
            tvCabinTitle = view.findViewById(R.id.tv_title);
            view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setCabinStatusPopup.dismiss();
                }
            });
            btnCabinConfirm = view.findViewById(R.id.btn_confirm);
            setCabinStatusPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        btnCabinConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCabinStatusPopup.dismiss();
                onSetCabinStatus(status);
            }
        });
        if (status == 1) {
            tvCabinTitle.setText("请确认是否清舱？");
        } else if (status == 2) {
            tvCabinTitle.setText("请确认是否完成？");
        } else {
            tvCabinTitle.setText("请确认是否开始卸货？");
        }
        setCabinStatusPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

    /**
     * 修改船的状态操作
     *
     * @param status 0|卸货;1|清舱;2|完成
     */
    private void onSetCabinStatus(final int status) {
        showLoadingPopup();
        String params = "{\"taskId\":\"" + taskId + "\",\"cabinNo\":\"" + cabinNo
                + "\",\"status\":\"" + status + "\""
                + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("params=" + params);
        RetrofitFactory.getInstance()
                .doSetCabinStatus(params)
                .compose(RxSchedulers.compose())
                .subscribe(new BaseObserver<BaseEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(BaseEntity entity) {
                        dismissLoadingPopup();
                        if (status == 1) {
                            tvStatus.setText("清舱");
                        } else if (status == 2) {
                            tvStatus.setText("完成");
                            btnComplete.setVisibility(View.GONE);
                        } else {
                            tvStatus.setText("卸货");
                        }
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        dismissLoadingPopup();
                        showTipPopup(msg + "");
                    }
                });
    }

    private Popup tipPopup;
    private TextView tvTitle;

    /**
     * 操作结果的提示信息弹窗
     */
    private void showTipPopup(String msg) {
        if (tipPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm, null);
            tvTitle = view.findViewById(R.id.tv_title);
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tipPopup.dismiss();
                }
            });
            tipPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        tvTitle.setText(msg + "");
        tipPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

}
