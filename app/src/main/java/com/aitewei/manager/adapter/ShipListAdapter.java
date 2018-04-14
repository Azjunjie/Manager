package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.ShipListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 船舶列表适配器
 * Created by zhangjunjie on 2017/11/8.
 */
public class ShipListAdapter extends BaseQuickAdapter<ShipListEntity.DataBean, BaseViewHolder> {

    public ShipListAdapter(int layoutResId, @Nullable List<ShipListEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShipListEntity.DataBean item) {
        helper.setText(R.id.tv_ship_name, item.getShipName() + "");
        String berthName = item.getBerthName();
        if ("1".equals(berthName)) {
            helper.setText(R.id.tv_time, "矿一");
        } else if ("2".equals(berthName)) {
            helper.setText(R.id.tv_time, "矿二");
        } else {
            helper.setText(R.id.tv_time, "");
        }
        helper.setText(R.id.tv_extra_time, "预靠时间：" + item.getEnter_port_time());
    }

}
