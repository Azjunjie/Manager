package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.common.Popup;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.CabinPositionEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.KeyBorardUtils;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置舱位
 */
public class SetCabinPositionActivity extends BaseActivity {

    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.content_view)
    LinearLayout contentView;
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.btn_refresh)
    FrameLayout btnRefresh;

    private String taskId;
    private int cabinNum;

    private List<CabinPositionEntity.DataBean> positionList;

    public static Intent getIntent(Context context, String taskId, int cabinNum
            , ArrayList<CabinPositionEntity.DataBean> positionList) {
        Intent intent = new Intent(context, SetCabinPositionActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("cabinNum", cabinNum);
        intent.putExtra("positionList", positionList);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_set_and_modify;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "确认锚点");
        KeyBorardUtils.setupUI(activity, parentLayout);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        cabinNum = intent.getIntExtra("cabinNum", 0);
        positionList = (List<CabinPositionEntity.DataBean>) intent.getSerializableExtra("positionList");
        initItem(positionList);

        contentView.setVisibility(View.VISIBLE);
        loadView.setVisibility(View.GONE);
//        loadView.setLoadType(LoadGroupView.TYPE_LOADING);
//        btnRefresh.setClickable(false);
//        requestData();

        btnRefresh.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyBorardUtils.closeSoftKeyBorard(activity);
    }

    private void requestData() {
        String params = "{\"taskId\":\"" + taskId + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("params=" + params);
        RetrofitFactory.getInstance()
                .doGetShipPosition(params)
                .compose(RxSchedulers.<CabinPositionEntity>compose())
                .subscribe(new BaseObserver<CabinPositionEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(CabinPositionEntity entity) {
                        dismissLoadingPopup();
                        contentView.setVisibility(View.VISIBLE);
                        loadView.setVisibility(View.GONE);
                        btnRefresh.setClickable(true);
                        positionList = entity.getData();
                        if (positionList != null && !positionList.isEmpty()) {
                            initItem(positionList);
                        } else {
                            onBindEmptyItem();
                        }
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        dismissLoadingPopup();
                        contentView.setVisibility(View.GONE);
                        loadView.setVisibility(View.VISIBLE);
                        loadView.setLoadError(msg + "");
                        btnRefresh.setClickable(false);
                    }
                });
    }

    /**
     * 未设置舱位
     */
    private void onBindEmptyItem() {
        if (positionList == null || positionList.isEmpty()) {
            positionList = new ArrayList<>();
        }
        positionList.clear();
        for (int i = 0; i < cabinNum; i++) {
            CabinPositionEntity.DataBean bean = new CabinPositionEntity.DataBean();
            bean.setCabinNo(i + 1 + "");
            bean.setStartPosition("");
            bean.setEndPosition("");
            positionList.add(bean);
        }
        initItem(positionList);
    }

    private void initItem(final List<CabinPositionEntity.DataBean> positionList) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        container.removeAllViews();
        int size = positionList.size();
        for (int i = 0; i < size; i++) {
            final CabinPositionEntity.DataBean dataBean = positionList.get(i);
            View view = inflater.inflate(R.layout.layout_cabin_position_set_item1, null);
            TextView tvCabinNo = view.findViewById(R.id.tv_cabin_no);
            EditText etStart = view.findViewById(R.id.et_start);
            EditText etEnd = view.findViewById(R.id.et_end);

            tvCabinNo.setText(dataBean.getCabinNo() + "");
            String startPosition = dataBean.getStartPosition();
            if (!TextUtils.isEmpty(startPosition) && Double.valueOf(startPosition) > 0) {
                etEnd.setText(startPosition + "");
                etEnd.setSelection(etStart.length());
            } else {
                etEnd.setText("");
                dataBean.setStartPosition("");
            }

            String endPosition = dataBean.getEndPosition();
            if (!TextUtils.isEmpty(endPosition) && Double.valueOf(endPosition) > 0) {
                etStart.setText(endPosition + "");
                etStart.setSelection(etEnd.length());
            } else {
                etStart.setText("");
                dataBean.setEndPosition("");
            }

            etStart.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    dataBean.setEndPosition(editable.toString() + "");
                }
            });
            etEnd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    dataBean.setStartPosition(editable.toString() + "");

//                        View childView = container.getChildAt(1);
//                        if (childView != null) {
//                            TextView tvStart = childView.findViewById(R.id.tv_start);
//                            if (tvStart != null) {
//                                CabinPositionEntity.DataBean bean = positionList.get(1);
//                                bean.setStartPosition(endPosition);
//                                tvStart.setText(endPosition);
//                            }
//                        }
                }
            });
//            if (i == 0) {
//            } else {
//                view = inflater.inflate(R.layout.layout_cabin_position_set_item2, null);
//                TextView tvCabinNo = view.findViewById(R.id.tv_cabin_no);
//                TextView tvStart = view.findViewById(R.id.tv_start);
//                EditText etEnd = view.findViewById(R.id.et_end);
//
//                tvCabinNo.setText(dataBean.getCabinNo() + "");
//                tvStart.setText(dataBean.getStartPosition() + "");
//                etEnd.setText(dataBean.getEndPosition() + "");
//                etEnd.setSelection(etEnd.length());
//
//                final int finalI = i;
//                etEnd.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        String endPosition = editable.toString() + "";
//                        dataBean.setEndPosition(endPosition);
//
//                        View childView = container.getChildAt(finalI + 1);
//                        if (childView != null) {
//                            TextView tvStart = childView.findViewById(R.id.tv_start);
//                            if (tvStart != null) {
//                                CabinPositionEntity.DataBean bean = positionList.get(finalI + 1);
//                                bean.setStartPosition(endPosition);
//                                tvStart.setText(endPosition);
//                            }
//                        }
//                    }
//                });
//            }
            container.addView(view);
        }
    }

    @OnClick({R.id.btn_refresh, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh:
                btnRefresh.setClickable(false);
                showLoadingPopup();
                requestData();
                break;
            case R.id.btn_complete:
                for (CabinPositionEntity.DataBean bean : positionList) {
                    String startPosition = bean.getStartPosition();
                    String endPosition = bean.getEndPosition();
                    LogUtil.e("cabinNo" + bean.getCabinNo() + ",startPosition=" + startPosition
                            + ",endPosition=" + endPosition);
                    if (!TextUtils.isEmpty(startPosition) || !TextUtils.isEmpty(endPosition)) {
                        double dStart = 0;
                        if (!TextUtils.isEmpty(startPosition)) {
                            dStart = Double.valueOf(startPosition);
                        }
                        double dEnd = 0;
                        if (!TextUtils.isEmpty(endPosition)) {
                            dEnd = Double.valueOf(endPosition);
                        }
                        if (dStart == 0 && dEnd == 0) {
                            //不做处理
                        } else {
                            if (dStart >= dEnd) {
                                showSetPositionFailurePopup(bean.getCabinNo() + "舱舱位起点位置应大于终点位置");
                                return;
                            }
                        }
                    }
                    if (TextUtils.isEmpty(startPosition)) {
                        bean.setStartPosition("0");
                    }
                    if (TextUtils.isEmpty(endPosition)) {
                        bean.setEndPosition("0");
                    }
                }
                showPositionConfirmPopup();
                break;
        }
    }

    private void onConfirm() {
        if (positionList != null && !positionList.isEmpty()) {
            String json = new Gson().toJson(positionList);
            json = "{\"taskId\":\"" + taskId + "\",\"userId\":\"" + User.newInstance().getUserId()
                    + "\"" + "," + "\"data\":" + json + "}";
            LogUtil.e("json=" + json);
            showLoadingPopup();
            RetrofitFactory.getInstance()
                    .doSetCabinPosition(json)
                    .compose(RxSchedulers.compose())
                    .subscribe(new BaseObserver<BaseEntity>(compositeDisposable) {
                        @Override
                        protected void onHandleSuccess(BaseEntity entity) {
                            dismissLoadingPopup();
                            showSetPositionSuccessPopup(entity.getMsg() + "");
                        }

                        @Override
                        protected void onHandleRequestError(String code, String msg) {
                            dismissLoadingPopup();
                            showSetPositionFailurePopup(msg);
                        }
                    });
        }
    }

    private Popup confirmPopup;

    private void showPositionConfirmPopup() {
        if (confirmPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm_cancle, null);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            tvTitle.setText("确认保存船舱位置？");
            view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmPopup.dismiss();
                }
            });
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmPopup.dismiss();
                    onConfirm();
                }
            });
            confirmPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        confirmPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

    private Popup setPositionSuccessPopup;
    private TextView tvSuccessTitle;

    private void showSetPositionSuccessPopup(String msg) {
        if (setPositionSuccessPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm, null);
            tvSuccessTitle = view.findViewById(R.id.tv_title);
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setPositionSuccessPopup.dismiss();
                    finish();
                }
            });
            setPositionSuccessPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        tvSuccessTitle.setText(msg + "");
        setPositionSuccessPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

    private Popup setPositionFailurePopup;
    private TextView tvFailureTitle;

    private void showSetPositionFailurePopup(String msg) {
        if (setPositionFailurePopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_default_confirm, null);
            tvFailureTitle = view.findViewById(R.id.tv_title);
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setPositionFailurePopup.dismiss();
                }
            });
            setPositionFailurePopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        tvFailureTitle.setText(msg + "");
        setPositionFailurePopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.CENTER, 0, 0);
    }

}
