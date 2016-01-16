package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.add.BillAddDialog;
import com.nexters.rainbow.rainbowcouple.common.CustomBaseFragment;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.EndlessListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillListFragment extends CustomBaseFragment implements DialogInterface.OnDismissListener {

    private final String TAG_BILL_LIST_FRAGMENT = "bill_list_fragment";
    private final String TAG_BILL_ADD_DIALOG = "bill_list_fragment";

    private View rootView;
    private List<Bill> billList = new ArrayList<>();
    private BillListAdapter billListAdapter;

    // TODO: 2016. 1. 16. listView loadNewData는 디비에 저장된 데이터 읽어오는 것으로
    @Bind(R.id.listViewBill) EndlessListView listViewBill;
    @Bind(R.id.textViewBillEmpty) TextView emptyTextView;
    @Bind(R.id.actionBtnAddBill) FloatingActionButton actionBtnAddBill;

    public static BillListFragment newInstance() {
        return new BillListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bill_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentTag(TAG_BILL_LIST_FRAGMENT);

        // TODO: 2016. 1. 16. db에서 데이터 읽어오기
        billList.add(new Bill(1L, 5000L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(2L, 100000L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(3L, 5200L, "테스트용 너의 데이터", TimeUtils.getDateTime(new Date()), false));
        billList.add(new Bill(4L, 3200L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(5L, 3200L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(6L, 12300L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(7L, 3240L, "테스트용 데이터", TimeUtils.getDateTime(new Date()), true));
        billList.add(new Bill(8L, 5200L, "테스트용 너의 데이터", TimeUtils.getDateTime(new Date()), false));
        billList.add(new Bill(9L, 5200L, "테스트용 너의 데이터", TimeUtils.getDateTime(new Date()), false));

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

    // TODO: 2016. 1. 16. option Menu에 동기화 버튼을 둬서 수동 동기화 하는 것은 어떨까 ?
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.actionBtnAddBill)
    public void showBillAddDialog() {
        BillAddDialog billAddDialog = BillAddDialog.newInstance();
        billAddDialog.setDismissListener(this);
        billAddDialog.show(getFragmentManager(), TAG_BILL_ADD_DIALOG);
    }

    /* add dialog가 dismiss될 때 */
    @Override
    public void onDismiss(DialogInterface dialog) {
        Snackbar.make(actionBtnAddBill, "새로운 지출 내역이 저장되었습니다.", Snackbar.LENGTH_SHORT).show();
    }
}