package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.GetUnloaderUnshipDetailListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 卸船机卸船明细的列表
 * Created by zhangjunjie on 2018/1/6.
 */

public class ShipUnloaderDetailProgressListAdapter extends BaseQuickAdapter<GetUnloaderUnshipDetailListEntity.DataBean, BaseViewHolder> {

    public ShipUnloaderDetailProgressListAdapter(int layoutResId, @Nullable List<GetUnloaderUnshipDetailListEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetUnloaderUnshipDetailListEntity.DataBean item) {
        helper.addOnClickListener(R.id.tv_cabin_no);
        helper.addOnClickListener(R.id.tv_cargoType);

        helper.setText(R.id.tv_cabin_no, item.getCabinNo() + "");
        helper.setText(R.id.tv_cargoType, item.getCargoName() + "");
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
