package com.aitewei.manager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipCabinListActivity;
import com.aitewei.manager.activity.ship.ShipCargoDetailActivity;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.PermissionsCode;
import com.aitewei.manager.entity.ShipCabinListEntity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 船舱列表适配器
 * Created by zhangjunjie on 2018/6/24.
 */

public class ShipCabinListAdapter extends BaseAdapter {
    private ShipCabinListActivity activity;
    private String taskId;
    private int type;
    private List<ShipCabinListEntity.DataBean> mListData;
    private LayoutInflater inflater;
    private DecimalFormat decimalFormat;

    public ShipCabinListAdapter(ShipCabinListActivity activity, String taskId, int type, List<ShipCabinListEntity.DataBean> mListData) {
        this.activity = activity;
        this.taskId = taskId;
        this.type = type;
        this.mListData = mListData;
        this.inflater = LayoutInflater.from(activity);
        decimalFormat = new DecimalFormat("0.0");
    }

    public void setmListData(List<ShipCabinListEntity.DataBean> mListData) {
        this.mListData = mListData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mListData != null) {
            return mListData.size() + 1;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_data, null);
            holder.tvCabinType = convertView.findViewById(R.id.tv_cabin_type);
            holder.tvTotal = convertView.findViewById(R.id.tv_total);
            holder.tvFinish = convertView.findViewById(R.id.tv_finish);
            holder.tvFinishBeforeClearance = convertView.findViewById(R.id.tv_finish_before_clearance);
            holder.tvRemainder = convertView.findViewById(R.id.tv_remainder);
            holder.tvClearance = convertView.findViewById(R.id.tv_clearance);
            holder.tvClearTime = convertView.findViewById(R.id.tv_clearTime);
            holder.tvStatus = convertView.findViewById(R.id.tv_status);
            holder.tvOperation = convertView.findViewById(R.id.tv_operation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        boolean hasPermission = PermissionsCode.isHasPermission(PermissionsCode.clearStatus);
        if (type == Constant.TYPE_WORKING && hasPermission) {
            holder.tvOperation.setVisibility(View.VISIBLE);
        } else {
            holder.tvOperation.setVisibility(View.GONE);
        }
        if (type == Constant.TYPE_COMING) {
            holder.tvClearTime.setVisibility(View.GONE);
        } else {
            holder.tvClearTime.setVisibility(View.VISIBLE);
        }
        int count = getCount() - 1;
        if (count == position) {
            ShipCabinListEntity.DataBean dataBean = new ShipCabinListEntity.DataBean();
            for (ShipCabinListEntity.DataBean bean : mListData) {
                dataBean.setTotal(dataBean.getTotal() + bean.getTotal());
                dataBean.setFinished(dataBean.getFinished() + bean.getFinished());
                dataBean.setFinishedBeforeClearance(dataBean.getFinishedBeforeClearance() + bean.getFinishedBeforeClearance());
                dataBean.setRemainder(dataBean.getRemainder() + bean.getRemainder());
                dataBean.setClearance(dataBean.getClearance() + bean.getClearance());
            }
            holder.tvCabinType.setText("--");
            holder.tvClearTime.setText("--");
            holder.tvCabinType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            holder.tvTotal.setText(decimalFormat.format(dataBean.getTotal()) + "");
            holder.tvFinish.setText(decimalFormat.format(dataBean.getFinished()) + "");
            holder.tvFinishBeforeClearance.setText(decimalFormat.format(dataBean.getFinishedBeforeClearance()) + "");
            holder.tvRemainder.setText(decimalFormat.format(dataBean.getRemainder()) + "");
            holder.tvClearance.setText(decimalFormat.format(dataBean.getClearance()) + "");
            holder.tvClearTime.setText("--");
            holder.tvStatus.setText("--");
            holder.tvOperation.setText("--");
            holder.tvOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        } else {
            final ShipCabinListEntity.DataBean dataBean = mListData.get(position);
            holder.tvCabinType.setText(dataBean.getCargoName() + "");
            holder.tvCabinType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(ShipCargoDetailActivity.getIntent(activity, taskId, dataBean.getCabinNo() + "", ""));
                }
            });
            holder.tvTotal.setText(dataBean.getTotal() + "");
            holder.tvFinish.setText(dataBean.getFinished() + "");
            holder.tvFinishBeforeClearance.setText(dataBean.getFinishedBeforeClearance() + "");
            holder.tvRemainder.setText(dataBean.getRemainder() + "");
            holder.tvClearance.setText(dataBean.getClearance() + "");
            holder.tvClearTime.setText(dataBean.getClearTime() + "");
            String status = dataBean.getStatus();//0|卸货;1|清舱;2|完成
            if ("0".equals(status)) {
                holder.tvStatus.setText("卸货");
                holder.tvOperation.setText("清舱");
            } else if ("1".equals(status)) {
                holder.tvStatus.setText("清舱");
                holder.tvOperation.setText("");
            } else if ("2".equals(status)) {
                holder.tvStatus.setText("完成");
                holder.tvOperation.setText("");
            } else {
                holder.tvStatus.setText("未开始");
                holder.tvOperation.setText("卸货");
            }
            holder.tvOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickOperationListener.onClickOperation(dataBean);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvCabinType;
        TextView tvTotal;
        TextView tvFinish;
        TextView tvFinishBeforeClearance;
        TextView tvRemainder;
        TextView tvClearance;
        TextView tvClearTime;
        TextView tvStatus;
        TextView tvOperation;
    }

    private OnClickOperationListener onClickOperationListener;

    public void setOnClickStatusListener(OnClickOperationListener onClickOperationListener) {
        this.onClickOperationListener = onClickOperationListener;
    }

    public interface OnClickOperationListener {
        void onClickOperation(ShipCabinListEntity.DataBean dataBean);
    }
}
