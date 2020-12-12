package com.aitewei.manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 封装ListView的适配器
 */
public abstract class AbsBaseListViewAdapter<T>
        extends BaseAdapter {
    protected List<T> list;
    private Context context;
    private int layoutId;

    /**
     * @param list     数据源
     * @param context  上下文
     * @param layoutId 布局文件id
     */
    public AbsBaseListViewAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, convertView, layoutId);
        setData(viewHolder, position);
        return viewHolder.getmConvertView();
    }

    /**
     * 给控件设置相应的数据
     *
     * @param viewHolder 数据的封装类
     * @param position   listview中 item的索引值
     */
    public abstract void setData(ViewHolder viewHolder, int position);

    /**
     * Class ViewHolder 用于实现控件的复用，减少内存开销
     */
    public static class ViewHolder {
        private View mConvertView;//用于返回给 listview adapter getview 方法的view

        /**
         * @param context  上下文
         * @param layoutId listview中item所对应的布局文件ID
         */
        private ViewHolder(Context context, int layoutId) {
            mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
            mConvertView.setTag(this);
        }

        /**
         * @param context     上下文实例
         * @param convertView
         * @param layoutId    listview中item所对应的布局文件ID
         * @return
         */
        public static ViewHolder getViewHolder(Context context, View convertView, int layoutId) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder(context, layoutId);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            return viewHolder;
        }

        public View getmConvertView() {
            return mConvertView;
        }

        /**
         * 获取View控件对象
         *
         * @param resId 控件id
         * @return
         */
        public View findView(int resId) {
            return mConvertView.findViewById(resId);
        }

        /**
         * 给相应的TextView设置内容
         *
         * @param resId       控件Id
         * @param viewContent 所要设置的内容
         * @return
         */
        public ViewHolder setViewText(int resId, String viewContent) {
            ((TextView) findView(resId)).setText(viewContent);
            return this;
        }
    }

}
