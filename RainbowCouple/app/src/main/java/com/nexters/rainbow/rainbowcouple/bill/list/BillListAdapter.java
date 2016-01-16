package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.utils.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillListAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;
    private List<Bill> dataList;
    private int layoutResourceId;

    private final String FORMAT_BILL_BUDGET = "₩,d";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA);

    public BillListAdapter(Context context, int layoutResourceId) {
        this(context, layoutResourceId, new ArrayList<Bill>());
    }

    public BillListAdapter(Context context, int layoutResourceId, List<Bill> dataList) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
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

    public void addAllData(List<Bill> data) {
        if (dataList == null) {
            if (data == null) {
                return;
            }
            dataList = new ArrayList<>();
        }

        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public BillItemViewHolder bindView(View parentView) {
        return new BillItemViewHolder(parentView);
    }

    public void loadView(View convertView, BillItemViewHolder viewHolder, Bill item, int position) {
        if (item.isMyBill()) {
            viewHolder.layoutPartnerBill.setVisibility(View.GONE);
            viewHolder.layoutMyBill.setVisibility(View.VISIBLE);
            viewHolder.textViewMyBillDate.setText(dateFormat.format(item.getCreatedAt()));
            viewHolder.textViewMyBillAmount.setText(String.format(FORMAT_BILL_BUDGET, item.getBudget()));
            viewHolder.textViewMyBillComment.setText(item.getComment());
        } else {
            viewHolder.layoutMyBill.setVisibility(View.GONE);
            viewHolder.layoutPartnerBill.setVisibility(View.VISIBLE);
            viewHolder.textViewPartnerBillDate.setText(dateFormat.format(item.getCreatedAt()));
            viewHolder.textViewPartnerBillAmount.setText(String.format(FORMAT_BILL_BUDGET, item.getBudget()));
            viewHolder.textViewPartnerBillComment.setText(item.getComment());
        }
    }

    class BillItemViewHolder {
        @Bind(R.id.layoutMyBill) LinearLayout layoutMyBill;
        @Bind(R.id.textViewMyBillDate) TextView textViewMyBillDate;
        @Bind(R.id.textViewMyBillAmount) TextView textViewMyBillAmount;
        @Bind(R.id.textViewMyBillComment) TextView textViewMyBillComment;
        @Bind(R.id.layoutPartnerBill) LinearLayout layoutPartnerBill;
        @Bind(R.id.textViewPartnerBillDate) TextView textViewPartnerBillDate;
        @Bind(R.id.textViewPartnerBillAmount) TextView textViewPartnerBillAmount;
        @Bind(R.id.textViewPartnerBillComment) TextView textViewPartnerBillComment;

        BillItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
