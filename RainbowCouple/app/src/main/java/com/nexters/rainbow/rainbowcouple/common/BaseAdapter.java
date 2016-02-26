package com.nexters.rainbow.rainbowcouple.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexters.rainbow.rainbowcouple.common.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <D> 데이터를 담은 객체
 * @param <H> ViewHolder
 * CustomListView를 보다 쉽게 만들기 위한 BaseAdapter
 */
public abstract class BaseAdapter<D, H> extends android.widget.BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<D> dataList;
    private int layoutResource;

    public BaseAdapter(Context context, int layoutResourceId) {
        this(context, layoutResourceId, new ArrayList<D>());
    }

    public BaseAdapter(Context context, int layoutResourceId, List<D> dataList) {
        this.context = context;
        this.layoutResource = layoutResourceId;
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void addData(D data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        dataList.add(data);
        notifyDataSetChanged();
    }

    public void addData(int location, D data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        dataList.add(location, data);
        notifyDataSetChanged();
    }

    public void addAllData(List<D> data) {
        if (dataList == null) {
            if (data == null) {
               return;
            }
            dataList = new ArrayList<>();
        }

        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public List<D> getDataList() {
        return dataList;
    }

    @Override
    public int getCount() {
        return CollectionUtils.isEmpty(dataList) ? 0 : dataList.size();
    }

    @Override
    public D getItem(int position) {
        if (CollectionUtils.isEmpty(dataList) || position > dataList.size())
            return null;
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H viewHolder;

        if (convertView != null) {
            viewHolder = (H) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = bindView(convertView);
            convertView.setTag(viewHolder);
        }

        loadView(convertView, viewHolder, getItem(position), position);

        return convertView;
    }

    public abstract H bindView(View convertView);

    public abstract void loadView(View convertView, H viewHolder, D item, int position);
}
