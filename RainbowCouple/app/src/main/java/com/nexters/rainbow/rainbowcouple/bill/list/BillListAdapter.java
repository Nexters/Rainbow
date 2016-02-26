package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
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

    @Override
    public void loadView(View convertView, BillItemViewHolder viewHolder, Bill item, int position) {
        if(item.getCategory().equals("식사")) {
            viewHolder.tvOwner.setBackgroundResource(R.drawable.ico_drink02);
        } else if (item.getCategory().equals("맥주")){

        }

        viewHolder.textViewBillCategory.setText(item.getCategory());
        viewHolder.textViewBillComment.setText(item.getComment());
        viewHolder.textViewBillAmount.setText(String.format(FORMAT_BILL_BUDGET, item.getAmount()));
    }

    public BillItemViewHolder bindView(View parentView) {
        return new BillItemViewHolder(parentView);
    }

    class BillItemViewHolder {
        @Bind(R.id.tvOwner) TextView tvOwner;
        @Bind(R.id.ivCategory) ImageView ivCategory;
        @Bind(R.id.textViewBillCategory) TextView textViewBillCategory;
        @Bind(R.id.textViewBillComment) TextView textViewBillComment;
        @Bind(R.id.textViewBillAmount) TextView textViewBillAmount;
        BillItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
