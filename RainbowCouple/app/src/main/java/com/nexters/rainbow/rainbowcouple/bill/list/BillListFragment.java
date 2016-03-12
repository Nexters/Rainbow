package com.nexters.rainbow.rainbowcouple.bill.list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.BillApi;
import com.nexters.rainbow.rainbowcouple.bill.OwnerType;
import com.nexters.rainbow.rainbowcouple.bill.add.BillAddActivity;
import com.nexters.rainbow.rainbowcouple.bill.add.BillAddDialog;
import com.nexters.rainbow.rainbowcouple.calendar.CalDate;
import com.nexters.rainbow.rainbowcouple.calendar.CalListAdapter;
import com.nexters.rainbow.rainbowcouple.calendar.CalendarManager;
import com.nexters.rainbow.rainbowcouple.calendar.WeeklyCalDate;
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
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

import static com.nexters.rainbow.rainbowcouple.bill.OwnerType.*;

public class BillListFragment extends BaseFragment implements BillAddDialog.AddDialogDismissCallback, CalListAdapter.CalendarItemSelectedListener {

    private static final String TAG_BILL_LIST_FRAGMENT = "bill_list_fragment";
    private static final int VIEW_PAGER_MAX_ITEM = 61;
    private static final int DEFAULT_VIEW_PAGER_PAGE_ITEM = (VIEW_PAGER_MAX_ITEM / 2);

    private SessionManager sessionManager;

    private View rootView;
    private List<Bill> billList = new ArrayList<>();
    private BillListAdapter billListAdapter;
    private CalListAdapter calListAdapter;

    private OwnerType ownerType;
    private Date viewDate;

    @Bind(R.id.calendar) ViewPager calendarView;
    @Bind(R.id.listViewBill) EndlessListView billListView;
    @Bind(R.id.actionBtnAddBill) Button actionBtnAddBill;
    @Bind(R.id.btnOur) Button btnOur;
    @Bind(R.id.btnMe) Button btnMe;
    @Bind(R.id.btnYou) Button btnYou;
    @Bind(R.id.textViewBillEmpty) TextView emptyTextView;
    @Bind(R.id.textViewBillTotalAmount) TextView billTotalAmount;
    @Bind(R.id.textViewYear) TextView textViewYear;
    @Bind(R.id.textViewMonth) TextView textViewMonth;
    @Bind(R.id.textViewDay) TextView textViewDay;
    @Bind(R.id.textViewOwner) TextView textViewOwner;
    @Bind(R.id.llCalendarGroup) LinearLayout llCanlendarGroup;

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

        loadCalendar();

        billListAdapter = new BillListAdapter(getActivity(), R.layout.list_item_bill, billList);
        billListView.setAdapter(billListAdapter);
        billListView.setEmptyView(emptyTextView);

        ownerType = OwnerType.ALL;
        viewDate = new Date();

        requestBills();

        changeViewByOwnerType();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.actionBtnAddBill)
    public void showBillAddDialog() {
//        BillAddDialog billAddDialog = BillAddDialog.newInstance();
//        billAddDialog.setDismissCallback(this);
//        billAddDialog.show(getFragmentManager(), billAddDialog.getFragmentTag());
        Intent billAddActivity = new Intent(getActivity(), BillAddActivity.class);
        startActivity(billAddActivity);

    }

    @Override
    public void notifySavedNewBill(Bill bill) {
        if (ObjectUtils.isEmpty(bill)) {
            DialogManager.showAlertDialog(getActivity(), "지출 내역 저장 중 오류가 발생했습니다.");
        }

        billListAdapter.addData(0, bill);
        billListAdapter.notifyDataSetChanged();
        billListView.setSelection(0);

        Snackbar.make(actionBtnAddBill, "새로운 지출 내역이 저장되었습니다.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void notifyError(Throwable throwable) {
        new ExceptionHandler(getActivity()).handle(throwable);
    }

    @Override
    public void selectDate(CalDate date) {
        viewDate = date.getDate();
        requestBills();
    }

    @OnClick(R.id.btnGraph)
    void openGraph() {
        Intent graphActivity = new Intent(getActivity(), GraphActivity.class);
        startActivity(graphActivity);
    }

    @OnClick(R.id.actionBtnRefresh)
    public void refreshBillList() {
        requestBills();
    }

    @OnClick({R.id.btnOur, R.id.btnMe, R.id.btnYou})
    void loadBillListByButtonClick(Button button) {
        switch (button.getId()) {
            case R.id.btnOur:
                ownerType = OwnerType.ALL;
                break;
            case R.id.btnMe:
                ownerType = OwnerType.MINE;
                break;
            case R.id.btnYou:
                ownerType = OwnerType.PARTNER;
                break;
        }

        requestBills();

        changeViewByOwnerType();
    }

    private void changeViewByOwnerType() {
        btnOur.setTextColor(Color.parseColor("#444444"));
        btnYou.setTextColor(Color.parseColor("#444444"));
        btnMe.setTextColor(Color.parseColor("#444444"));

        switch (ownerType) {
            case ALL:
                llCanlendarGroup.setBackgroundResource(R.drawable.top_bg_our01);
                billTotalAmount.setTextColor(Color.parseColor("#9195B0"));
                btnOur.setTextColor(Color.parseColor("#9195B0"));
                break;
            case MINE:
                btnMe.setTextColor(Color.parseColor("#95BEC9"));
                billTotalAmount.setTextColor(Color.parseColor("#95BEC9"));
                llCanlendarGroup.setBackgroundResource(R.drawable.top_bg_me01);
                break;
            case PARTNER:
                btnYou.setTextColor(Color.parseColor("#D3Bf89"));
                billTotalAmount.setTextColor(Color.parseColor("#D3Bf89"));
                llCanlendarGroup.setBackgroundResource(R.drawable.top_bg_you01);
                break;
        }
    }

    private void requestBills() {
        final BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<List<Bill>> billObservable = billApi.viewBillByDay(
                sessionManager.getUserToken(),
                ownerType,
                String.valueOf(TimeUtils.getYearOfDate(viewDate)),
                String.valueOf(TimeUtils.getMonthOfDate(viewDate)),
                String.valueOf(TimeUtils.getDayOfDate(viewDate))
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
    }


    private void setBillViewData(List<Bill> bills) {
        textViewYear.setText(String.valueOf(TimeUtils.getYearOfDate(viewDate)));
        textViewMonth.setText(String.valueOf(TimeUtils.getMonthOfDate(viewDate)));
        textViewDay.setText(String.valueOf(TimeUtils.getDayOfDate(viewDate)));

        if (MINE.equals(ownerType)) {
            textViewOwner.setText("내가");
        } else if (PARTNER.equals(ownerType)) {
            textViewOwner.setText("파트너가");
        } else {
            textViewOwner.setText("우리가");
        }

        billTotalAmount.setText(String.format(Constants.FORMAT_BILL_BUDGET, getTotalAmount(bills)));
        billListAdapter.addAllData(bills);
        billListAdapter.notifyDataSetChanged();
    }

    private void loadCalendar() {
        List<WeeklyCalDate> calDateList = CalendarManager.makeCalDateList();

        calListAdapter = new CalListAdapter(getActivity(), R.layout.list_item_calendar, calDateList);
        calendarView.setAdapter(calListAdapter);
        calendarView.setCurrentItem(DEFAULT_VIEW_PAGER_PAGE_ITEM);
        calListAdapter.setSelectedListener(this);

    }

    private int getTotalAmount(List<Bill> bills) {
        //TODO : 아예 서버에서 total Amount를 받아오도록
        int totalAmount = 0;
        for (Bill bill : bills) {
            totalAmount += bill.getAmount();
        }
        return totalAmount;
    }

}