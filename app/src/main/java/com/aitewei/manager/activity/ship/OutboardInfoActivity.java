package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.CabinProgressListAdapter;
import com.aitewei.manager.adapter.OutboardInfoListAdapter;
import com.aitewei.manager.adapter.ShipCabinListAdapter;
import com.aitewei.manager.adapter.ShipCabinNoListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.OutboardInfoStatistics;
import com.aitewei.manager.entity.QueryOutboardInfoRemind;
import com.aitewei.manager.entity.ShipCabinListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.LoadGroupView;
import com.aitewei.manager.view.NoscrollListView;
import com.aitewei.manager.view.SyncHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import butterknife.BindView;

/**
 * 舱外作业量统计
 */
public class OutboardInfoActivity extends BaseActivity {
    @BindView(R.id.load_view)
    LoadGroupView loadView;

    @BindView(R.id.lv_left)
    NoscrollListView mLeft;
    @BindView(R.id.lv_data)
    NoscrollListView mData;
    @BindView(R.id.header_horizontal)
    SyncHorizontalScrollView mHeaderHorizontal;
    @BindView(R.id.data_horizontal)
    SyncHorizontalScrollView mDataHorizontal;
    @BindView(R.id.content_view)
    ScrollView contentView;

    private String taskId;
    private OutboardInfoListAdapter mLeftAdapter;
    private OutboardInfoListAdapter mDataAdapter;

    private List<OutboardInfoStatistics.DataBean> list;

    public static void start(Context context, String taskId) {
        Intent starter = new Intent(context, OutboardInfoActivity.class);
        starter.putExtra("taskId", taskId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_outboard_info;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "舱外作业量统计");

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            taskId = intent.getStringExtra("taskId");

            loadView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            requestOutboardInfoStatistics();
        }
    }

    private void requestOutboardInfoStatistics() {
        String params = "{\"taskId\":\"" + taskId + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        RetrofitFactory.getInstance()
                .doOutboardInfoStatistics(params)
                .compose(RxSchedulers.<OutboardInfoStatistics>compose())
                .subscribe(new BaseObserver<OutboardInfoStatistics>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(OutboardInfoStatistics entity) {
                        list = entity.getData();
                        if (list != null && !list.isEmpty()) {
                            if (loadView != null) {
                                loadView.setVisibility(View.GONE);
                                contentView.setVisibility(View.VISIBLE);

                                mLeftAdapter = new OutboardInfoListAdapter(activity, list, R.layout.item_left);
                                mLeft.setAdapter(mLeftAdapter);
                                mLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        OutboardInfoStatistics.DataBean bean = list.get(position);
                                        startActivity(ShipCabinDetailActivity.getIntent(activity, taskId, bean.getCabinNo() + ""));
                                    }
                                });

                                mDataAdapter = new OutboardInfoListAdapter(activity, list, R.layout.layout_out_board_info_list_item);
                                mData.setAdapter(mDataAdapter);
                            }
                        } else {
                            if (loadView != null) {
                                contentView.setVisibility(View.GONE);
                                loadView.setVisibility(View.VISIBLE);
                                loadView.setLoadType(LoadGroupView.TYPE_EMPTY);
                            }
                        }
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        if (loadView != null) {
                            loadView.setVisibility(View.VISIBLE);
                            contentView.setVisibility(View.GONE);
                            loadView.setLoadError(msg + "");
                        }
                    }
                });
    }

}
