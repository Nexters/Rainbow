package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.BillApi;
import com.nexters.rainbow.rainbowcouple.bill.OwnerType;
import com.nexters.rainbow.rainbowcouple.bill.add.BillAddDialog;
import com.nexters.rainbow.rainbowcouple.common.BaseFragment;
import com.nexters.rainbow.rainbowcouple.common.Constants;
import com.nexters.rainbow.rainbowcouple.common.network.ExceptionHandler;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.network.SessionManager;
import com.nexters.rainbow.rainbowcouple.common.utils.DialogManager;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;
import com.nexters.rainbow.rainbowcouple.common.widget.EndlessListView;
import com.nexters.rainbow.rainbowcouple.graph.GraphActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class BillListFragment extends BaseFragment implements BillAddDialog.AddDialogDismissCallback {

    private static final String TAG_BILL_LIST_FRAGMENT = "bill_list_fragment";

    private SessionManager sessionManager;

    private View rootView;
    private List<Bill> billList = new ArrayList<>();
    private BillListAdapter billListAdapter;

    private OwnerType ownerType;

    @Bind(R.id.listViewBill) EndlessListView billListView;
    @Bind(R.id.textViewBillEmpty) TextView emptyTextView;
    @Bind(R.id.actionBtnAddBill) Button actionBtnAddBill;
    @Bind(R.id.textViewBillTotalAmount) TextView billTotalAmount;

    public static BillListFragment newInstance() {
        BillListFragment fragment = new BillListFragment();
        fragment.setFragmentTag(TAG_BILL_LIST_FRAGMENT);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bill_list, container, false);
        ButterKnife.bind(this, rootView);

        setFragmentTag(TAG_BILL_LIST_FRAGMENT);

        sessionManager = SessionManager.getInstance(getActivity());

        billListAdapter = new BillListAdapter(getActivity(), R.layout.list_item_bill, billList);
        billListView.setAdapter(billListAdapter);
        billListView.setEmptyView(emptyTextView);

        //TODO : 나 너 우리 일 경우 ownerType에 구분해서 넣어줄 것.
        ownerType = OwnerType.ALL;

        final BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<List<Bill>> billObservable = billApi.viewBillByMonth(
                sessionManager.getUserToken(),
                ownerType,
                String.valueOf(TimeUtils.getYearOfToday()),
                String.valueOf(TimeUtils.getMonthOfToday())
        );

        bind(billObservable)
                .subscribe(new Action1<List<Bill>>() {
                    @Override
                    public void call(List<Bill> bills) {
                        setBillViewData(bills);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        new ExceptionHandler(getActivity()).handle(throwable);
                    }
                });

        return rootView;
    }


    private void setBillViewData(List<Bill> bills) {
        billTotalAmount.setText(String.format(Constants.FORMAT_BILL_BUDGET, getTotalAmount(bills)));
        billListAdapter.addAllData(bills);
    }

    private int getTotalAmount(List<Bill> bills) {
        //TODO : 아예 서버에서 total Amount를 받아오도록
        int totalAmount = 0;
        for (Bill bill : bills) {
            totalAmount += bill.getAmount();
        }
        return totalAmount;
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
        billAddDialog.setDismissCallback(this);
        billAddDialog.show(getFragmentManager(), billAddDialog.getFragmentTag());
    }

    // TODO: 2016. 1. 16. api로 서버에 저장 할 것. DB에 따로 저장하지는 않음..
    // TODO: 2016. 1. 16. listView 자체 저장 하고 DB는 fragment 다시 불러올 때 서버와 동기화 한 후 가져올 것
    @Override
    public void notifySavedNewBill(Bill bill) {
        if (ObjectUtils.isEmpty(bill)) {
            DialogManager.showAlertDialog(getActivity(), "지출 내역 저장 중 오류가 발생했습니다.");
        }

        billListAdapter.addData(0, bill);
        billListView.setSelection(0);

        Snackbar.make(actionBtnAddBill, "새로운 지출 내역이 저장되었습니다.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void notifyError(Throwable throwable) {
        new ExceptionHandler(getActivity()).handle(throwable);
    }


    @OnClick(R.id.btnGraph)
    void openGraph() {
        Intent graphActivity = new Intent(getActivity(), GraphActivity.class);
        startActivity(graphActivity);
    }

    @OnClick(R.id.btnOur)
    void loadOurBill() {

    }

    @OnClick(R.id.btnMe)
    void loadMyBill() {

    }

    @OnClick(R.id.btnYou)
    void loadYourBill() {

    }
}