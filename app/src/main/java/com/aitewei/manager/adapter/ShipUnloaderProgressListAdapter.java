package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.GetUnloaderUnshipInfoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 卸船机卸船进度的列表
 * Created by zhangjunjie on 2018/1/6.
 */

public class ShipUnloaderProgressListAdapter extends BaseQuickAdapter<GetUnloaderUnshipInfoEntity.DataBean, BaseViewHolder> {

    public ShipUnloaderProgressListAdapter(int layoutResId, @Nullable List<GetUnloaderUnshipInfoEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetUnloaderUnshipInfoEntity.DataBean item) {
        helper.addOnClickListener(R.id.tv_name);

        helper.setText(R.id.tv_name, item.getUnloaderName() + "");
        helper.setText(R.id.tv_usedTime, item.getUsedTime() + "");
        helper.setText(R.id.tv_unloading, item.getUnloading() + "");
        helper.setText(R.id.tv_efficiency, item.getEfficiency() + "");
    }
}
