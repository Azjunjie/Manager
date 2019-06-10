package com.aitewei.manager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipCargoDetailActivity;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.entity.CarbinInfoStatisticsEntity;

import java.util.List;

/**
 * 货物进度统计列表
 * Created by zhangjunjie on 2018/1/6.
 */

public class CabinProgressStatisticsListAdapter extends AbsBaseListViewAdapter<CarbinInfoStatisticsEntity.DataBean> {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private Context context;
    private String cargoId;
    private int dataType;//1-左 2-右
    private int showType;//效率、进度显示类别
    private List<CarbinInfoStatisticsEntity.DataBean> list;

    /**
     * @param context  上下文
     * @param list     数据源
     * @param layoutId 布局文件id
     */
    public CabinProgressStatisticsListAdapter(Context context, String cargoId, int dataType, int showType, List<CarbinInfoStatisticsEntity.DataBean> list, int layoutId) {
        super(context, list, layoutId);
        this.context = context;
        this.cargoId = cargoId;
        this.dataType = dataType;
        this.showType = showType;
        this.list = list;
    }

    @Override
    public void setData(ViewHolder viewHolder, final int position) {
        final CarbinInfoStatisticsEntity.DataBean dataBean = list.get(position);
        if (dataType == LEFT) {
            TextView tvName = (TextView) viewHolder.findView(R.id.tv_cabin_no);
            viewHolder.setViewText(R.id.tv_cabin_no, dataBean.getCabinNo() + "");

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
                    String status = dataBean.getStatus();
                    TextView tvStatus = (TextView) viewHolder.findView(R.id.tv_status);
                    tvStatus.setVisibility(View.VISIBLE);
                    if ("0".equals(status)) {
                        tvStatus.setText("卸货");
                    } else if ("1".equals(status)) {
                        tvStatus.setText("清舱");
                    } else if ("2".equals(status)) {
                        tvStatus.setText("完成");
                    } else {
                        tvStatus.setText("--");
                    }
                    break;
            }
            TextView tvCargoType = (TextView) viewHolder.findView(R.id.tv_cargoType);
            if (!TextUtils.isEmpty(cargoId)) {
                tvCargoType.setVisibility(View.GONE);
            } else {
                tvCargoType.setVisibility(View.VISIBLE);
                viewHolder.setViewText(R.id.tv_cargoType, dataBean.getCargoName() + "");
                tvCargoType.setTextColor(Color.parseColor("#1296db"));
                tvCargoType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(
                                ShipCargoDetailActivity.getIntent(context, "", "", dataBean.getCargoId()));
                    }
                });
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
