package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.common.BaseAdapter;
import com.nexters.rainbow.rainbowcouple.common.utils.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillListAdapter extends BaseAdapter<Bill, BillListAdapter.BillItemViewHolder> {

    private Context context;

    private LayoutInflater layoutInflater;
    private List<Bill> dataList;
    private int layoutResourceId;

    private final String FORMAT_BILL_BUDGET = "₩,d";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA);

    public BillListAdapter(Context context, int layoutResourceId, List<Bill> dataList) {
        super(context, layoutResourceId, dataList);
        this.context = context;
        this.dataList = dataList;
        this.layoutResourceId = layoutResourceId;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillItemViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (BillItemViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResourceId, parent, false);
            viewHolder = bindView(convertView);
            convertView.setTag(viewHolder);
        }

        loadView(convertView, viewHolder, getItem(position), position);

        return convertView;
    }

    public void addData(Bill data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        dataList.add(data);
        notifyDataSetChanged();
    }


    @Override
    public void loadView(View convertView, BillItemViewHolder viewHolder, Bill item, int position) {
        viewHolder.textViewBillCategory.setText(item.getCategory());
        viewHolder.textViewBillComment.setText(item.getComment());
        viewHolder.textViewBillAmount.setText(String.format(FORMAT_BILL_BUDGET, item.getAmount()));
    }

    @Override
    public int getCount() {
        return CollectionUtils.isEmpty(dataList) ? 0 : dataList.size();
    }

    @Override
    public Bill getItem(int position) {
        if (CollectionUtils.isEmpty(dataList) || position > dataList.size())
            return null;
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public BillItemViewHolder bindView(View parentView) {
        return new BillItemViewHolder(parentView);
    }


    class BillItemViewHolder {
        @Bind(R.id.textViewBillCategory) TextView textViewBillCategory;
        @Bind(R.id.textViewBillComment) TextView textViewBillComment;
        @Bind(R.id.textViewBillAmount) TextView textViewBillAmount;
        BillItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
