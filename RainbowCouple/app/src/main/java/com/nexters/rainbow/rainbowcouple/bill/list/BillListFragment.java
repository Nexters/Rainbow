package com.nexters.rainbow.rainbowcouple.bill.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.CustomBaseFragment;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.EndlessListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillListFragment extends CustomBaseFragment {

    private final String TAG_FRAGMENT_BILL_LIST = "bill_list_fragment";

    private List<Bill> billList = new ArrayList<>();
    private BillListAdapter billListAdapter;

    @Bind(R.id.listViewBill) EndlessListView listViewBill;
    @Bind(R.id.textViewBillEmpty) TextView emptyTextView;

    public static BillListFragment newInstance() {
        return new BillListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bill_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentTag(TAG_FRAGMENT_BILL_LIST);

        billList.add(new Bill(1L, 5000L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(2L, 100000L, "테스트용 너의 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(3L, 5200L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), false));
        billList.add(new Bill(4L, 3200L, "테스트용 너의 데이터", TimeUtils.getDateTime(new Date()), true));

        billListAdapter = new BillListAdapter(getActivity(), R.layout.list_item_bill, billList);
        listViewBill.setAdapter(billListAdapter);
        listViewBill.setEmptyView(emptyTextView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}