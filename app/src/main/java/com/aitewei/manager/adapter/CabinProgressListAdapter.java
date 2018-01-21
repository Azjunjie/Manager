package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.GetCargoUnshipInfoEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 货物卸船进度的列表
 * Created by zhangjunjie on 2018/1/6.
 */

public class CabinProgressListAdapter extends BaseQuickAdapter<GetCargoUnshipInfoEntity.DataBean, BaseViewHolder> {

    public CabinProgressListAdapter(int layoutResId, @Nullable List<GetCargoUnshipInfoEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetCargoUnshipInfoEntity.DataBean item) {
        helper.addOnClickListener(R.id.tv_cargoType);

        helper.setText(R.id.tv_cargoType, item.getCargoName() + "");
        helper.setText(R.id.tv_total, item.getTotal() + "");
        helper.setText(R.id.tv_finish, item.getFinished() + "");
        helper.setText(R.id.tv_remainder, item.getRemainder() + "");
        helper.setText(R.id.tv_clearance, item.getClearance() + "");
    }
}
