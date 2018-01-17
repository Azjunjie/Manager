package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.CabinProgressListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetCargoUnshipInfoEntity;
import com.aitewei.manager.entity.GetShipUnshipInfoEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 以货物为主卸货总进度
 */
public class CargoProgressActivity extends BaseActivity {
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private CabinProgressListAdapter adapter;
    private String taskId;

    public static Intent getIntent(Context context, String taskId) {
        Intent intent = new Intent(context, CargoProgressActivity.class);
        intent.putExtra("taskId", taskId);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cargo_progress;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸船进度");

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

        adapter = new CabinProgressListAdapter(R.layout.layout_cabin_progress_list_item, null);
        listView.setAdapter(adapter);

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        requestData();
    }

    private void requestData() {
        String params = "{\"taskId\":" + taskId + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("doGetCargoUnshipInfo json=" + params);
        Observable<GetCargoUnshipInfoEntity> cargoObservable = RetrofitFactory
                .getInstance()
                .doGetCargoUnshipInfo(params);
        Observable<GetShipUnshipInfoEntity> shipObservable = RetrofitFactory
                .getInstance()
                .doGetShipUnshipInfo(params);
        Disposable disposable = Observable.zip(cargoObservable, shipObservable, new BiFunction<GetCargoUnshipInfoEntity
                , GetShipUnshipInfoEntity, List<GetCargoUnshipInfoEntity.DataBean>>() {
            @Override
            public List<GetCargoUnshipInfoEntity.DataBean> apply(GetCargoUnshipInfoEntity cargoUnshipInfoEntity
                    , GetShipUnshipInfoEntity shipUnshipInfoEntity) throws Exception {
                if ("1".equals(cargoUnshipInfoEntity.getCode())
                        && "1".equals(shipUnshipInfoEntity.getCode())) {
                    List<GetCargoUnshipInfoEntity.DataBean> beanList = cargoUnshipInfoEntity.getData();
                    GetShipUnshipInfoEntity.DataBean bean = shipUnshipInfoEntity.getData();
                    GetCargoUnshipInfoEntity.DataBean dataBean = new GetCargoUnshipInfoEntity.DataBean();
                    dataBean.setCargoName("合计");
                    dataBean.setTotal(bean.getTotal());
                    dataBean.setFinished(bean.getFinished());
                    dataBean.setRemainder(bean.getRemainder());
                    dataBean.setClearance(bean.getClearance());
                    beanList.add(dataBean);
                    return beanList;
                }
                String msg = cargoUnshipInfoEntity.getMsg();
                String shipMsg = shipUnshipInfoEntity.getMsg();
                if (!TextUtils.isEmpty(msg)) {
                    throw new Exception(msg + "");
                } else if (!TextUtils.isEmpty(shipMsg)) {
                    throw new Exception(shipMsg + "");
                } else {
                    throw new Exception("网络异常，请稍后重试");
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GetCargoUnshipInfoEntity.DataBean>>() {
                    @Override
                    public void accept(List<GetCargoUnshipInfoEntity.DataBean> dataBeans) throws Exception {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                        loadView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        adapter.setNewData(dataBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                        loadView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        loadView.setLoadError(throwable.getMessage() + "");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });
        compositeDisposable.add(disposable);

//        RetrofitFactory.getInstance()
//                .doGetCargoUnshipInfo(params)
//                .compose(RxSchedulers.<GetCargoUnshipInfoEntity>compose())
//                .subscribe(new BaseObserver<GetCargoUnshipInfoEntity>(compositeDisposable) {
//                    @Override
//                    protected void onHandleSuccess(GetCargoUnshipInfoEntity entity) {
//                        loadView.setVisibility(View.GONE);
//                        listView.setVisibility(View.VISIBLE);
//
//                        List<GetCargoUnshipInfoEntity.DataBean> beanList = entity.getData();
//                        GetCargoUnshipInfoEntity.DataBean dataBean = new GetCargoUnshipInfoEntity.DataBean();
//                        dataBean.setCargoName("合计");
//                        for (GetCargoUnshipInfoEntity.DataBean bean : beanList) {
//                            dataBean.setTotal(dataBean.getTotal() + bean.getTotal());
//                            dataBean.setFinished(dataBean.getFinished() + bean.getFinished());
//                            dataBean.setRemainder(dataBean.getRemainder() + bean.getRemainder());
//                            dataBean.setClearance(dataBean.getClearance() + bean.getClearance());
//                        }
//                        beanList.add(dataBean);
//                        adapter.setNewData(beanList);
//                    }
//
//                    @Override
//                    protected void onHandleRequestError(String code, String msg) {
//                        loadView.setVisibility(View.VISIBLE);
//                        listView.setVisibility(View.GONE);
//                        loadView.setLoadError(msg + "");
//                    }
//                });
    }

}
