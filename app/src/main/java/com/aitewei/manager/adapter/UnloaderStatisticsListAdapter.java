package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.UnloaderInfoStatisticsEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 卸船机统计的列表
 * Created by zhangjunjie on 2018/1/6.
 */

public class UnloaderStatisticsListAdapter extends BaseQuickAdapter<UnloaderInfoStatisticsEntity.DataBean, BaseViewHolder> {

    public UnloaderStatisticsListAdapter(int layoutResId, @Nullable List<UnloaderInfoStatisticsEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnloaderInfoStatisticsEntity.DataBean item) {
        helper.addOnClickListener(R.id.tv_name);

        helper.setText(R.id.tv_name, item.getUnloaderName() + "");
        helper.setText(R.id.tv_usedTime, item.getUsedTime() + "");
        helper.setText(R.id.tv_unloading, item.getUnloading() + "");
        double efficiency = item.getEfficiency();
        if (efficiency == 0) {
            helper.setText(R.id.tv_efficiency, "--");
        } else {
            helper.setText(R.id.tv_efficiency, efficiency + "");
        }
    }
}
