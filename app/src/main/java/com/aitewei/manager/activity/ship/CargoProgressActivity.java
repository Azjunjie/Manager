package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.CabinProgressListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetCargoUnshipInfoEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;

import java.util.List;

import butterknife.BindView;

/**
 * 以货物为主卸货总进度
 */
public class CargoProgressActivity extends BaseActivity {
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.list_view)
    RecyclerView listView;

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

        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setHasFixedSize(true);

        adapter = new CabinProgressListAdapter(R.layout.layout_cabin_progress_list_item, null);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        taskId = getIntent().getStringExtra("taskId");

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        requestData();
    }

    private void requestData() {
        String params = "{\"taskId\":" + taskId + ",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("doGetCargoUnshipInfo json=" + params);
        RetrofitFactory.getInstance()
                .doGetCargoUnshipInfo(params)
                .compose(RxSchedulers.<GetCargoUnshipInfoEntity>compose())
                .subscribe(new BaseObserver<GetCargoUnshipInfoEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(GetCargoUnshipInfoEntity entity) {
                        loadView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        List<GetCargoUnshipInfoEntity.DataBean> beanList = entity.getData();
                        GetCargoUnshipInfoEntity.DataBean dataBean = new GetCargoUnshipInfoEntity.DataBean();
                        dataBean.setCargoName("合计");
                        for (GetCargoUnshipInfoEntity.DataBean bean : beanList) {
                            dataBean.setTotal(dataBean.getTotal() + bean.getTotal());
                            dataBean.setFinished(dataBean.getFinished() + bean.getFinished());
                            dataBean.setRemainder(dataBean.getRemainder() + bean.getRemainder());
                            dataBean.setClearance(dataBean.getClearance() + bean.getClearance());
                        }
                        beanList.add(dataBean);
                        adapter.setNewData(beanList);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        loadView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        loadView.setLoadError(msg + "");
                    }
                });
    }

}
