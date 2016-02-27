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
        switch(item.getCategory()) {
            case "식사" :
                viewHolder.tvOwner.setBackgroundResource(R.drawable.ico_meal01);
                viewHolder.ivCategory.setImageResource(R.drawable.ico_meal02);
                break;
            case "음료" :
                viewHolder.tvOwner.setBackgroundResource(R.drawable.ico_drink01);
                viewHolder.ivCategory.setImageResource(R.drawable.ico_drink02);
                break;
            case "쇼핑" :
                viewHolder.tvOwner.setBackgroundResource(R.drawable.icon_shopping01);
                viewHolder.ivCategory.setImageResource(R.drawable.icon_shopping02);
                break;
            case "영화" :
                viewHolder.tvOwner.setBackgroundResource(R.drawable.ico_movie01);
                viewHolder.ivCategory.setImageResource(R.drawable.ico_movie02);
                break;
            case "오락" :
                viewHolder.tvOwner.setBackgroundResource(R.drawable.ico_game01);
                viewHolder.ivCategory.setImageResource(R.drawable.ico_game02);
                break;
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
