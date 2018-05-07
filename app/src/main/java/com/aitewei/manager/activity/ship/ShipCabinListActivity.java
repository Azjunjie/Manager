package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.PermissionsCode;
import com.aitewei.manager.common.Popup;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.CabinPositionEntity;
import com.aitewei.manager.entity.ShipCabinListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.aitewei.manager.view.NoscrollListView;
import com.aitewei.manager.view.SyncHorizontalScrollView;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 船舶舱位列表详情
 */
public class ShipCabinListActivity extends BaseActivity {

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.content_view)
    ScrollView contentView;

    @BindView(R.id.btn_ship_info)
    TextView btnShipInfo;
    @BindView(R.id.tv_clearTime)
    TextView tvClearTime;
    @BindView(R.id.tv_operation)
    TextView tvOperation;

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
    @BindView(R.id.btn_modify_location)
    Button btnModifyLocation;
    @BindView(R.id.btn_begin)
    Button btnBegin;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    @BindView(R.id.btn_menu)
    FrameLayout btnMenu;

    private LeftAdapter mLeftAdapter;
    private DataAdapter mDataAdapter;

    private List<ShipCabinListEntity.DataBean> mListData;

    private String taskId;
    private int type;
    private ShipCabinListEntity.DataBean operationBean;//当前设置船舱状态的bean
    private int cabinNum;
    private String shipName;
    private DecimalFormat decimalFormat;

    public static Intent getIntent(Context context, int type, String taskId, String shipName) {
        Intent intent = new Intent(context, ShipCabinListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("taskId", taskId);
        intent.putExtra("shipName", shipName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_cabin_list;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸船情况");

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

        type = getIntent().getIntExtra("type", -1);
        if (type == Constant.TYPE_WORKING) {
            tvClearTime.setVisibility(View.VISIBLE);
            btnBegin.setVisibility(View.GONE);
            btnMenu.setVisibility(View.VISIBLE);
            if (PermissionsCode.isHasPermission(PermissionsCode.clearStatus)) {
                tvOperation.setVisibility(View.VISIBLE);
            } else {
                tvOperation.setVisibility(View.GONE);
            }
            if (PermissionsCode.isHasPermission(PermissionsCode.setLocation)) {
                btnModifyLocation.setVisibility(View.VISIBLE);
            } else {
                btnModifyLocation.setVisibility(View.GONE);
            }
            if (PermissionsCode.isHasPermission(PermissionsCode.completeShip)) {
                btnComplete.setVisibility(View.VISIBLE);
            } else {
                btnComplete.setVisibility(View.GONE);
            }
        } else if (type == Constant.TYPE_COMING) {
            tvClearTime.setVisibility(View.GONE);
            tvOperation.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
            btnMenu.setVisibility(View.GONE);
            if (PermissionsCode.isHasPermission(PermissionsCode.setLocation)) {
                btnModifyLocation.setVisibility(View.VISIBLE);
            } else {
                btnModifyLocation.setVisibility(View.GONE);
            }
            if (PermissionsCode.isHasPermission(PermissionsCode.shipBerthing)) {
                btnBegin.setVisibility(View.VISIBLE);
            } else {
                btnBegin.setVisibility(View.GONE);
            }
        } else {
            tvClearTime.setVisibility(View.VISIBLE);
            tvOperation.setVisibility(View.GONE);
            btnModifyLocation.setVisibility(View.GONE);
            btnBegin.setVisibility(View.GONE);
            btnComplete.setVisibility(View.GONE);
            btnMenu.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        decimalFormat = new DecimalFormat("0.00");

        taskId = getIntent().getStringExtra("taskId");
        shipName = getIntent().getStringExtra("shipName");

        btnShipInfo.setText(shipName + "");

        btnRefresh.setClickable(false);
        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListData != null) {
            showLoadingPopup();
        }
        requestCabinListData();
        handler.sendEmptyMessageDelayed(1, 1000 * 60 * 3);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeMessages(1);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.e("refresh data=====");
            requestCabinListData();
            handler.sendEmptyMessageDelayed(1, 1000 * 60 * 3);
        }
    };

    private void requestCabinListData() {
        String params = "{\"taskId\":\"" + taskId + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        RetrofitFactory.getInstance()
                .doGetCabinList(params)
                .compose(RxSchedulers.<ShipCabinListEntity>compose())
                .subscribe(new BaseObserver<ShipCabinListEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(ShipCabinListEntity entity) {
                        dismissLoadingPopup();
                        btnRefresh.setClickable(true);
                        loadView.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);
                        cabinNum = entity.getTotal();
                        mListData = entity.getData();
                        mLeftAdapter.notifyDataSetChanged();
                        mDataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        dismissLoadingPopup();
                        btnRefresh.setClickable(true);
                        contentView.setVisibility(View.GONE);
                        loadView.setVisibility(View.VISIBLE);
                        loadView.setLoadError(msg + "");
                    }
                });
    }

    private void initAdapter() {
        mLeftAdapter = new LeftAdapter();
        mLeft.setAdapter(mLeftAdapter);

        mDataAdapter = new DataAdapter();
        mDataAdapter.setOnClickStatusListener(new OnClickOperationListener() {
            @Override
            public void onClickOperation(final ShipCabinListEntity.DataBean dataBean) {
                if (PermissionsCode.isHasPermission(PermissionsCode.clearStatus)) {
                    operationBean = dataBean;
                    String status = dataBean.getStatus();//0|卸货;1|清舱;2|完成
                    if ("0".equals(status)) {
                        //清舱操作提示
                        showSetCabinStatusPopup(1);
                    } else if ("1".equals(status)) {
                        //完成操作提示
//                        showSetCabinStatusPopup(2);
                    } else if ("2".equals(status)) {
                    } else {
                        //卸货操作提示
                        showSetCabinStatusPopup(0);
                    }
                }
            }
        });
        mData.setAdapter(mDataAdapter);
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
        String params = "{\"taskId\":\"" + taskId + "\",\"cabinNo\":\"" + operationBean.getCabinNo()
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
                            operationBean.setStatus("1");
                        } else if (status == 2) {
                            operationBean.setStatus("2");
                        } else {
                            operationBean.setStatus("0");
                        }
                        mDataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        dismissLoadingPopup();
                        showTipPopup(msg + "");
                    }
                });
    }

    @OnClick({R.id.btn_refresh, R.id.btn_ship_info, R.id.btn_modify_location, R.id.btn_begin
            , R.id.btn_complete, R.id.btn_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh://刷新
                btnRefresh.setClickable(false);
                showLoadingPopup();
                requestCabinListData();
                break;
            case R.id.btn_ship_info://查看船舶详情
                startActivity(ShipBaseInfoActivity.getIntent(activity, taskId));
                break;
            case R.id.btn_modify_location://修改锚点
                if (mListData != null) {
                    ArrayList<CabinPositionEntity.DataBean> positionList = new ArrayList<>();
                    for (ShipCabinListEntity.DataBean bean : mListData) {
                        CabinPositionEntity.DataBean dataBean = new CabinPositionEntity.DataBean();
                        dataBean.setCabinNo(bean.getCabinNo() + "");
                        dataBean.setStartPosition(bean.getStartPosition() + "");
                        dataBean.setEndPosition(bean.getEndPosition() + "");
                        positionList.add(dataBean);
                    }
                    startActivity(SetCabinPositionActivity.getIntent(activity, taskId, cabinNum, positionList));
                } else {
                    ToastUtils.show(activity, "请先刷新获取船舱列表");
                }
                break;
            case R.id.btn_begin://开始卸船
                showSetShipStatusPopup(0);
                break;
            case R.id.btn_complete://结束卸船
                showSetShipStatusPopup(1);
                break;
            case R.id.btn_menu://菜单
                showMenuPopup();
                break;
        }
    }

    private Popup menuPopup;

    /**
     * 菜单弹窗
     */
    private void showMenuPopup() {
        if (menuPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_cabin_list_menu, null);
            view.findViewById(R.id.btn_progress).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获货物为主查看卸船进度
                    menuPopup.dismiss();
                    startActivity(CargoProgressActivity.getIntent(activity, taskId, shipName + ""));
                }
            });
            view.findViewById(R.id.btn_progress1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //以卸船机为主查看总进度
                    menuPopup.dismiss();
                    startActivity(ShipUnloaderProgressActivity.getIntent(activity, taskId));
                }
            });
            view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuPopup.dismiss();
                }
            });
            menuPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        menuPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
    }

    private Popup setShipStatusPopup;
    private TextView tvShipStatusTitle;
    private TextView btnShipConfirm;

    /**
     * 设置船状态的确认弹窗
     *
     * @param status 0|开始卸船、1|结束卸船
     */
    private void showSetShipStatusPopup(final int status) {
        if (setShipStatusPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm_cancle, null);
            tvShipStatusTitle = view.findViewById(R.id.tv_title);
            view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setShipStatusPopup.dismiss();
                }
            });
            btnShipConfirm = view.findViewById(R.id.btn_confirm);
            setShipStatusPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        btnShipConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShipStatusPopup.dismiss();
                onSetShipStatus(status);
            }
        });
        if (status == 1) {
            tvShipStatusTitle.setText("请确认是否结束卸船？");
        } else {
            tvShipStatusTitle.setText("请确认是否开始卸船？");
        }
        setShipStatusPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

    /**
     * 修改船的状态操作
     *
     * @param status 0|开始卸船、1|结束卸船
     */
    private void onSetShipStatus(final int status) {
        showLoadingPopup();
        String params = "{\"taskId\":\"" + taskId + "\",\"status\":\"" + status + "\""
                + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("params=" + params);
        RetrofitFactory.getInstance()
                .doSetShipStatus(params)
                .compose(RxSchedulers.compose())
                .subscribe(new BaseObserver<BaseEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(BaseEntity entity) {
                        dismissLoadingPopup();
                        if (status == 1) {
                            EventBus.getDefault().post("FinishedShip");
                        } else {
                            EventBus.getDefault().post("BeginShip");
                        }
                        finish();
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

    private class LeftAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mListData != null) {
                return mListData.size() + 1;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ShipCabinListActivity.this).inflate(R.layout.item_left, null);
                holder.tvCabinNo = convertView.findViewById(R.id.tv_cabin_no);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int count = getCount() - 1;
            if (position == count) {
                holder.tvCabinNo.setText("合计");
                holder.tvCabinNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            } else {
                ShipCabinListEntity.DataBean dataBean = mListData.get(position);
                final int cabinNo = dataBean.getCabinNo();
                holder.tvCabinNo.setText(cabinNo + "");
                holder.tvCabinNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(ShipCabinDetailActivity.getIntent(activity, taskId, cabinNo));
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            TextView tvCabinNo;
        }
    }

    private class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mListData != null) {
                return mListData.size() + 1;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ShipCabinListActivity.this).inflate(R.layout.item_data, null);
                holder.tvCabinType = convertView.findViewById(R.id.tv_cabin_type);
                holder.tvTotal = convertView.findViewById(R.id.tv_total);
                holder.tvFinish = convertView.findViewById(R.id.tv_finish);
                holder.tvFinishBeforeClearance = convertView.findViewById(R.id.tv_finish_before_clearance);
                holder.tvRemainder = convertView.findViewById(R.id.tv_remainder);
                holder.tvClearance = convertView.findViewById(R.id.tv_clearance);
                holder.tvClearTime = convertView.findViewById(R.id.tv_clearTime);
                holder.tvStatus = convertView.findViewById(R.id.tv_status);
                holder.tvOperation = convertView.findViewById(R.id.tv_operation);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            boolean hasPermission = PermissionsCode.isHasPermission(PermissionsCode.clearStatus);
            if (type == Constant.TYPE_WORKING && hasPermission) {
                holder.tvOperation.setVisibility(View.VISIBLE);
            } else {
                holder.tvOperation.setVisibility(View.GONE);
            }
            if (type == Constant.TYPE_COMING) {
                holder.tvClearTime.setVisibility(View.GONE);
            } else {
                holder.tvClearTime.setVisibility(View.VISIBLE);
            }
            int count = getCount() - 1;
            if (count == position) {
                ShipCabinListEntity.DataBean dataBean = new ShipCabinListEntity.DataBean();
                for (ShipCabinListEntity.DataBean bean : mListData) {
                    dataBean.setTotal(dataBean.getTotal() + bean.getTotal());
                    dataBean.setFinished(dataBean.getFinished() + bean.getFinished());
                    dataBean.setFinishedBeforeClearance(dataBean.getFinishedBeforeClearance() + bean.getFinishedBeforeClearance());
                    dataBean.setRemainder(dataBean.getRemainder() + bean.getRemainder());
                    dataBean.setClearance(dataBean.getClearance() + bean.getClearance());
                }
                holder.tvCabinType.setText("--");
                holder.tvClearTime.setText("--");
                holder.tvCabinType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                holder.tvTotal.setText(decimalFormat.format(dataBean.getTotal()) + "");
                holder.tvFinish.setText(decimalFormat.format(dataBean.getFinished()) + "");
                holder.tvFinishBeforeClearance.setText(decimalFormat.format(dataBean.getFinishedBeforeClearance()) + "");
                holder.tvRemainder.setText(decimalFormat.format(dataBean.getRemainder()) + "");
                holder.tvClearance.setText(decimalFormat.format(dataBean.getClearance()) + "");
                holder.tvClearTime.setText("--");
                holder.tvStatus.setText("--");
                holder.tvOperation.setText("--");
                holder.tvOperation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            } else {
                final ShipCabinListEntity.DataBean dataBean = mListData.get(position);
                holder.tvCabinType.setText(dataBean.getCargoName() + "");
                holder.tvCabinType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(ShipCargoDetailActivity.getIntent(activity, taskId, dataBean.getCabinNo()));
                    }
                });
                holder.tvTotal.setText(dataBean.getTotal() + "");
                holder.tvFinish.setText(dataBean.getFinished() + "");
                holder.tvFinishBeforeClearance.setText(dataBean.getFinishedBeforeClearance() + "");
                holder.tvRemainder.setText(dataBean.getRemainder() + "");
                holder.tvClearance.setText(dataBean.getClearance() + "");
                holder.tvClearTime.setText(dataBean.getClearTime() + "");
                String status = dataBean.getStatus();//0|卸货;1|清舱;2|完成
                if ("0".equals(status)) {
                    holder.tvStatus.setText("卸货");
                    holder.tvOperation.setText("清舱");
                } else if ("1".equals(status)) {
                    holder.tvStatus.setText("清舱");
                    holder.tvOperation.setText("");
                } else if ("2".equals(status)) {
                    holder.tvStatus.setText("");
                    holder.tvOperation.setText("");
                } else {
                    holder.tvStatus.setText("未开始");
                    holder.tvOperation.setText("卸货");
                }
                holder.tvOperation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickOperationListener.onClickOperation(dataBean);
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            TextView tvCabinType;
            TextView tvTotal;
            TextView tvFinish;
            TextView tvFinishBeforeClearance;
            TextView tvRemainder;
            TextView tvClearance;
            TextView tvClearTime;
            TextView tvStatus;
            TextView tvOperation;
        }

        private OnClickOperationListener onClickOperationListener;

        public void setOnClickStatusListener(OnClickOperationListener onClickOperationListener) {
            this.onClickOperationListener = onClickOperationListener;
        }
    }

    private interface OnClickOperationListener {
        void onClickOperation(ShipCabinListEntity.DataBean dataBean);
    }

}
