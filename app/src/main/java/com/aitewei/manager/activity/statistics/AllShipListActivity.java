package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aitewei.manager.R;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.utils.ToolBarUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 所有进港的船舶
 */
public class AllShipListActivity extends BaseActivity {
    @BindView(R.id.list_view)
    RecyclerView listView;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AllShipListActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_ship_list;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "选择船舶");
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        List<String> list = initList();
        AllShipListAdapter adapter = new AllShipListAdapter(R.layout.layout_ship_list_item, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("shipName", "船舶" + (position + 1));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        listView.setAdapter(adapter);
    }

    private List<String> initList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("船舶" + (i + 1));
        }
        return list;
    }

    private class AllShipListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public AllShipListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_ship_name, item + "");
        }
    }

}
