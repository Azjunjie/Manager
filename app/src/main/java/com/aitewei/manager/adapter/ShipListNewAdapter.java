package com.aitewei.manager.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

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
public class ShipListNewAdapter extends BaseQuickAdapter<ShipListEntity.DataBean, BaseViewHolder> {

    public ShipListNewAdapter(int layoutResId, @Nullable List<ShipListEntity.DataBean> data) {
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

        int layoutPosition = helper.getLayoutPosition();
        TextView tvType = helper.getView(R.id.tv_type);
        int shipType = item.getShipType();
        switch (shipType) {
            case Constant.TYPE_COMING:
                int comingPosition = onGetTypeFristPosition(Constant.TYPE_COMING);
                if (layoutPosition == comingPosition) {
                    tvType.setText("预靠船舶");
                    tvType.setVisibility(View.VISIBLE);
                } else {
                    tvType.setVisibility(View.GONE);
                }
                helper.setText(R.id.tv_extra_time, "预靠时间：2018-04-01 12:00");
                break;
            case Constant.TYPE_WORKING:
                int workingPosition = onGetTypeFristPosition(Constant.TYPE_WORKING);
                if (layoutPosition == workingPosition) {
                    tvType.setText("作业船舶");
                    tvType.setVisibility(View.VISIBLE);
                } else {
                    tvType.setVisibility(View.GONE);
                }
                helper.setText(R.id.tv_extra_time, "靠泊时间：2018-04-01 12:00");
                break;
        }
    }

    private int onGetTypeFristPosition(int type) {
        List<ShipListEntity.DataBean> list = getData();
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ShipListEntity.DataBean bean = list.get(i);
                int shipType = bean.getShipType();
                if (type == shipType) {
                    return i;
                }
            }
        }
        return -1;
    }

}
