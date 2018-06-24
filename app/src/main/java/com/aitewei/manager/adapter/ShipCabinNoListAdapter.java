package com.aitewei.manager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.activity.ship.ShipCabinDetailActivity;
import com.aitewei.manager.activity.ship.ShipCabinListActivity;
import com.aitewei.manager.entity.ShipCabinListEntity;

import java.util.List;

/**
 * 船舱号列表
 * Created by zhangjunjie on 2018/6/24.
 */

public class ShipCabinNoListAdapter extends BaseAdapter {
    private ShipCabinListActivity activity;
    private String taskId;
    private List<ShipCabinListEntity.DataBean> mListData;
    private LayoutInflater inflater;

    public ShipCabinNoListAdapter(ShipCabinListActivity activity, String taskId, List<ShipCabinListEntity.DataBean> mListData) {
        this.activity = activity;
        this.taskId = taskId;
        this.mListData = mListData;
        this.inflater = LayoutInflater.from(activity);
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
            convertView = inflater.inflate(R.layout.item_left, null);
            holder.tvCabinNo = convertView.findViewById(R.id.tv_cabin_no);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int count = getCount() - 1;
        if (position == count) {
            holder.tvCabinNo.setText("合计");
        } else {
            ShipCabinListEntity.DataBean dataBean = mListData.get(position);
            int cabinNo = dataBean.getCabinNo();
            holder.tvCabinNo.setText(cabinNo + "");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvCabinNo;
    }
}
