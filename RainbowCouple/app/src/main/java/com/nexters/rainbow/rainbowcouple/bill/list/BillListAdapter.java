package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.common.BaseAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillListAdapter extends BaseAdapter<Bill, BillListAdapter.BillItemViewHolder> {

    private final String FORMAT_BILL_BUDGET = "₩%,d";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA);

    public BillListAdapter(Context context, int layoutResourceId, List<Bill> dataList) {
        super(context, layoutResourceId, dataList);
    }

    public BillItemViewHolder bindView(View parentView) {
        return new BillItemViewHolder(parentView);
    }

    @Override
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
