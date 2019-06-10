package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.GetUploaderDetailEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 卸船机详细
 * Created by zhangjunjie on 2018/5/13.
 */

public class UnloadListAdapter extends BaseQuickAdapter<GetUploaderDetailEntity.UnloaderDetailBean, BaseViewHolder> {

    public UnloadListAdapter(int layoutResId, @Nullable List<GetUploaderDetailEntity.UnloaderDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetUploaderDetailEntity.UnloaderDetailBean item) {
        helper.setText(R.id.tv_unloaderName, item.getUnloaderName() + "");
        helper.setText(R.id.tv_startTime, item.getStartTime() + "");
        helper.setText(R.id.tv_endTime, item.getEndTime() + "");
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