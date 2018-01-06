package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.CabinProgressListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.entity.ShipCabinListEntity;
import com.aitewei.manager.utils.ToolBarUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 每船的卸货总进度
 */
public class ShipCabinTotalActivity extends BaseActivity {
    @BindView(R.id.list_view)
    RecyclerView listView;

    public static Intent getIntent(Context context, ArrayList<ShipCabinListEntity.DataBean> list) {
        Intent intent = new Intent(context, ShipCabinTotalActivity.class);
        intent.putExtra("list", list);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_cabin_total;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸船进度");

        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        ArrayList<ShipCabinListEntity.DataBean> list =
                (ArrayList<ShipCabinListEntity.DataBean>) getIntent().getSerializableExtra("list");
        CabinProgressListAdapter adapter = new CabinProgressListAdapter(R.layout.layout_cabin_progress_list_item, list);
        listView.setAdapter(adapter);
    }

}
