package com.aitewei.manager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.entity.CargoInfoStatisticsEntity;

import java.util.List;

/**
 * 货物进度统计列表
 * Created by zhangjunjie on 2018/1/6.
 */

public class CargoProgressStatisticsListAdapter extends AbsBaseListViewAdapter<CargoInfoStatisticsEntity.DataBean> {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private int dataType;//1-左 2-右
    private int showType;//效率、进度显示类别
    private List<CargoInfoStatisticsEntity.DataBean> list;

    /**
     * @param context  上下文
     * @param list     数据源
     * @param layoutId 布局文件id
     */
    public CargoProgressStatisticsListAdapter(Context context, int dataType, int showType, List<CargoInfoStatisticsEntity.DataBean> list, int layoutId) {
        super(context, list, layoutId);
        this.dataType = dataType;
        this.showType = showType;
        this.list = list;
    }

    @Override
    public void setData(ViewHolder viewHolder, final int position) {
        CargoInfoStatisticsEntity.DataBean dataBean = list.get(position);
        if (dataType == LEFT) {
            TextView tvName = (TextView) viewHolder.findView(R.id.tv_cabin_no);
            viewHolder.setViewText(R.id.tv_cabin_no, dataBean.getCargoName() + "");

            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemChildClick != null) {
                        onItemChildClick.onItemChildClick(position);
                    }
                }
            });
        } else {
            switch (showType) {
                case Constant.TYPE_PROGRESS://查看进度
                    break;
                case Constant.TYPE_EFFICAIENCY://查看效率
                    viewHolder.setViewText(R.id.tv_finishedUsedTime, dataBean.getFinishedUsedTime() + "");
                    double finishedEfficiency = dataBean.getFinishedEfficiency();
                    if (finishedEfficiency == 0) {
                        viewHolder.setViewText(R.id.tv_finishedEfficiency, "--");
                    } else {
                        viewHolder.setViewText(R.id.tv_finishedEfficiency, finishedEfficiency + "");
                    }
                    viewHolder.setViewText(R.id.tv_finishedUsedTime_before_clearance, dataBean.getFinishedUsedTimeBeforeClearance() + "");
                    double finishedEfficiencyBeforeClearance = dataBean.getFinishedEfficiencyBeforeClearance();
                    if (finishedEfficiencyBeforeClearance == 0) {
                        viewHolder.setViewText(R.id.tv_finishedEfficiency_before_clearance, "--");
                    } else {
                        viewHolder.setViewText(R.id.tv_finishedEfficiency_before_clearance, finishedEfficiencyBeforeClearance + "");
                    }
                    viewHolder.setViewText(R.id.tv_clearanceUsedTime, dataBean.getClearanceUsedTime() + "");
                    double clearanceEfficiency = dataBean.getClearanceEfficiency();
                    if (clearanceEfficiency == 0) {
                        viewHolder.setViewText(R.id.tv_clearanceEfficiency, "--");
                    } else {
                        viewHolder.setViewText(R.id.tv_clearanceEfficiency, clearanceEfficiency + "");
                    }
                    break;
            }
            viewHolder.setViewText(R.id.tv_total, dataBean.getTotal() + "");
            viewHolder.setViewText(R.id.tv_finished, dataBean.getFinished() + "");
            viewHolder.setViewText(R.id.tv_finish_before_clearance, dataBean.getFinishedBeforeClearance() + "");
            viewHolder.setViewText(R.id.tv_remainder, dataBean.getRemainder() + "");
            viewHolder.setViewText(R.id.tv_clearance, dataBean.getClearance() + "");
        }
    }

    public interface OnItemChildClick {
        void onItemChildClick(int position);
    }

    private OnItemChildClick onItemChildClick;

    public void setOnItemChildClick(OnItemChildClick onItemChildClick) {
        this.onItemChildClick = onItemChildClick;
    }
}
