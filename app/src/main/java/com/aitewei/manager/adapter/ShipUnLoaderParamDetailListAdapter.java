package com.aitewei.manager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.GetUnloaderPlcParamDetailListEntity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 卸船机PLC参数信息列表适配器
 */
public class ShipUnLoaderParamDetailListAdapter
        extends AbsBaseListViewAdapter<GetUnloaderPlcParamDetailListEntity.DataBean> {

    private DecimalFormat decimalFormat;

    /**
     * @param context  上下文
     * @param list     数据源
     * @param layoutId 布局文件id
     */
    public ShipUnLoaderParamDetailListAdapter(Context context,
                                              List<GetUnloaderPlcParamDetailListEntity.DataBean> list,
                                              int layoutId) {
        super(context, list, layoutId);
        decimalFormat = new DecimalFormat("0.00");
    }

    @Override
    public void setData(ViewHolder viewHolder, int position) {
        GetUnloaderPlcParamDetailListEntity.DataBean bean = list.get(position);
        viewHolder.setViewText(R.id.tv_unloaderName, bean.getUnloaderId() + "");
        viewHolder.setViewText(R.id.tv_deliveryRate, decimalFormat.format(bean.getDeliveryRate()) + "");
        viewHolder.setViewText(R.id.tv_doumenOpeningDegree, decimalFormat.format(bean.getDoumenOpeningDegree()) + "");
        viewHolder.setViewText(R.id.tv_hopperLoad, decimalFormat.format(bean.getHopperLoad()) + "");

        String systemState = bean.getSystemState() + "";
        viewHolder.setViewText(R.id.tv_systemState, systemState);
        TextView tvSystemState = (TextView) viewHolder.findView(R.id.tv_systemState);
        if ("在线".equals(systemState)) {
            tvSystemState.setTextColor(Color.parseColor("#898989"));
        } else {
            tvSystemState.setTextColor(Color.parseColor("#db1912"));
        }
    }

}
