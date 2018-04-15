package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;

import com.aitewei.manager.R;
import com.aitewei.manager.common.Constant;
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
        String shipStatus = item.getShipStatus();
        if ((Constant.TYPE_COMING + "").equals(shipStatus)) {
            helper.setText(R.id.tv_extra_time, "预靠时间：" + item.getEnterPortTime());
            helper.setText(R.id.tv_extra_time2, "--");
        } else if ((Constant.TYPE_WORKING + "").equals(shipStatus)) {
            helper.setText(R.id.tv_extra_time, "靠泊时间：" + item.getBerthingTime());
            helper.setText(R.id.tv_extra_time2, "开工时间：" + item.getBeginTime());
        } else if ((Constant.TYPE_FINISH + "").equals(shipStatus)) {
            helper.setText(R.id.tv_extra_time, "开工时间：" + item.getBeginTime());
            helper.setText(R.id.tv_extra_time2, "完工时间：" + item.getEndTime());
        }
    }

}
