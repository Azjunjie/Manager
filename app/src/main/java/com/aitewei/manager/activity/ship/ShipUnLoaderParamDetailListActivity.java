package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.ShipUnLoaderParamDetailListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetUnloaderPlcParamDetailListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.ScreenUtils;
import com.aitewei.manager.view.LoadGroupView;
import com.aitewei.manager.view.NoscrollListView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 卸船机PLC参数信息界面
 */
public class ShipUnLoaderParamDetailListActivity extends BaseActivity {
    private static int REFRESH_TIME = 10000;

    @BindView(R.id.btn_back)
    FrameLayout btnBack;
    @BindView(R.id.btn_refresh)
    FrameLayout btnRefresh;
    @BindView(R.id.tool_bar)
    FrameLayout toolBar;
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.list_view)
    NoscrollListView listView;
    @BindView(R.id.content_view)
    LinearLayout contentView;

    private ShipUnLoaderParamDetailListAdapter adapter;
    private List<GetUnloaderPlcParamDetailListEntity.DataBean> list;

    private boolean isFirstLoaded;
    private BaseObserver<GetUnloaderPlcParamDetailListEntity> observer;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ShipUnLoaderParamDetailListActivity.class);
        return intent;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    onRefreshData();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_unloade_param_detail_list;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolBar.getLayoutParams();
            layoutParams.topMargin = ScreenUtils.getStatusHeight(activity);
            toolBar.setLayoutParams(layoutParams);
        }

        btnRefresh.setClickable(false);
        loadView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);

        adapter = new ShipUnLoaderParamDetailListAdapter(this, list, R.layout.item_ship_unloader_param_detail_list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        requestShipUnLoaderParamDetailData();
    }

    private void requestShipUnLoaderParamDetailData() {
        String params = "{\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        observer = new BaseObserver<GetUnloaderPlcParamDetailListEntity>(compositeDisposable) {
            @Override
            protected void onHandleSuccess(GetUnloaderPlcParamDetailListEntity entity) {
                dismissLoadingPopup();
                if (btnRefresh != null) {
                    btnRefresh.setClickable(true);
                }
                if (contentView.getVisibility() != View.VISIBLE) {
                    loadView.setVisibility(View.GONE);
                    contentView.setVisibility(View.VISIBLE);
                }
                dismissLoadingPopup();
                list = entity.getData();
                adapter.setList(list);
                if (handler != null) {
                    handler.sendEmptyMessageDelayed(0, REFRESH_TIME);
                }
            }

            @Override
            protected void onHandleRequestError(String code, String msg) {
                dismissLoadingPopup();
                if (btnRefresh != null) {
                    btnRefresh.setClickable(true);
                }
                if (contentView.getVisibility() == View.VISIBLE) {
                    contentView.setVisibility(View.GONE);
                    loadView.setVisibility(View.VISIBLE);
                }
                loadView.setLoadError(msg + "");
                if (handler != null) {
                    handler.sendEmptyMessageDelayed(0, REFRESH_TIME);
                }
            }
        };
        RetrofitFactory.getInstance()
                .doGetUnloaderPlcParamDetailList(params)
                .compose(RxSchedulers.<GetUnloaderPlcParamDetailListEntity>compose())
                .subscribe(observer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null && isFirstLoaded) {
            handler.sendEmptyMessage(0);
        } else {
            isFirstLoaded = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeMessages(0);
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finishActivity();
                break;
            case R.id.btn_refresh:
                if (handler != null) {
                    handler.removeMessages(0);
                }
                onRefreshData();
                break;
        }
    }

    private void onRefreshData() {
        if (observer != null && observer.getDisposable() != null) {
            observer.getDisposable().dispose();
        }
        btnRefresh.setClickable(false);
        showLoadingPopup();
        requestShipUnLoaderParamDetailData();
    }

}
