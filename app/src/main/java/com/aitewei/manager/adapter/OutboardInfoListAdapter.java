package com.aitewei.manager.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.entity.OutboardInfoStatistics;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 舱外作业量统计列表适配器
 */
public class OutboardInfoListAdapter extends AbsBaseListViewAdapter<OutboardInfoStatistics.DataBean> {
    private List<OutboardInfoStatistics.DataBean> list;

    /**
     * @param context  上下文
     * @param list     数据源
     * @param layoutId 布局文件id
     */
    public OutboardInfoListAdapter(Context context, List<OutboardInfoStatistics.DataBean> list, int layoutId) {
        super(context, list, layoutId);
        this.list = list;
    }

    public void setList(List<OutboardInfoStatistics.DataBean> list) {
        this.list = list;
    }

    @Override
    public void setData(ViewHolder helper, int position) {
        OutboardInfoStatistics.DataBean item = list.get(position);

        TextView tvCabinNo = (TextView) helper.findView(R.id.tv_cabin_no);
        if (tvCabinNo != null) {
            tvCabinNo.setText(item.getCabinNo() + "");
        } else {
            helper.setViewText(R.id.tv_startPosition, item.getStartPosition() + "");
            helper.setViewText(R.id.tv_endPosition, item.getEndPosition() + "");
            helper.setViewText(R.id.tv_leftOffset, item.getLeftOffset() + "");
            helper.setViewText(R.id.tv_leftShovelNumber, item.getLeftShovelNumber() + "");
            helper.setViewText(R.id.tv_leftUnloading, item.getLeftUnloading() + "");
            helper.setViewText(R.id.tv_rightOffset, item.getRightOffset() + "");
            helper.setViewText(R.id.tv_rightShovelNumber, item.getRightShovelNumber() + "");
            helper.setViewText(R.id.tv_rightUnloading, item.getRightUnloading() + "");
        }
    }
}
