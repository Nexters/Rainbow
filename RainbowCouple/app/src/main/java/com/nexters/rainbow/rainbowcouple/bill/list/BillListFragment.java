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

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class BillListFragment extends BaseFragment implements BillAddDialog.AddDialogDismissCallback {

    private static final String TAG_BILL_LIST_FRAGMENT = "bill_list_fragment";
    private static final int VIEW_PAGER_MAX_ITEM = 61;
    private static final int DEFAULT_VIEW_PAGER_PAGE_ITEM = (VIEW_PAGER_MAX_ITEM / 2) - 1;

    private SessionManager sessionManager;

    private View rootView;
    private List<Bill> billList = new ArrayList<>();
    private List<CalDate> calDateList = new ArrayList<>();
    private BillListAdapter billListAdapter;
    private CalListAdapter calListAdapter;

    private OwnerType ownerType;

    @Bind(R.id.listViewBill) EndlessListView billListView;
    @Bind(R.id.textViewBillEmpty) TextView emptyTextView;
    @Bind(R.id.actionBtnAddBill) Button actionBtnAddBill;
    @Bind(R.id.textViewBillTotalAmount) TextView billTotalAmount;
    @Bind(R.id.btnOur) Button btnOur;
    @Bind(R.id.btnMe) Button btnMe;
    @Bind(R.id.btnYou) Button btnYou;
    @Bind(R.id.llCalendarGroup) LinearLayout llCanlendarGroup;
    @Bind(R.id.calendar) ViewPager calendarView;

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

        //TODO : 나 너 우리 일 경우 ownerType에 구분해서 넣어줄 것.
        loadOurBill();

        return rootView;
    }

    private void loadCalendar() {
        List<WeeklyCalDate> calDateList = makeCalDateList();

        calListAdapter = new CalListAdapter(getActivity(), R.layout.list_item_calendar, calDateList);
        calendarView.setAdapter(calListAdapter);
        calendarView.setCurrentItem(DEFAULT_VIEW_PAGER_PAGE_ITEM);

    }

    private List<WeeklyCalDate> makeCalDateList() {
        List<WeeklyCalDate> calDateList = new ArrayList<>();

        DateTime mondayOfWeek = new DateTime(CalendarManager.getFirstDayOfWeek());

        /* 현재 날짜 부터 30주 전 까지 weekly date */
        for (int weekCount = 30; weekCount > 0; weekCount--) {
            DateTime pastMondayOfWeek = mondayOfWeek.minusWeeks(weekCount);
            WeeklyCalDate weeklyCalDate = CalendarManager.makeWeeklyCalDate(pastMondayOfWeek.toDate());
            calDateList.add(weeklyCalDate);
        }

        /* 현재 날짜가 들어간 주간 */
        WeeklyCalDate currentWeeklyCalDate = CalendarManager.makeWeeklyCalDate(mondayOfWeek.toDate());
        calDateList.add(currentWeeklyCalDate);

        /* 현재 날짜 부터 30주 후 까지 weekly date */
        for (int weekCount = 1; weekCount < 31; weekCount++) {
            DateTime nextMondayOfWeek = mondayOfWeek.plusWeeks(weekCount);
            WeeklyCalDate weeklyCalDate = CalendarManager.makeWeeklyCalDate(nextMondayOfWeek.toDate());
            calDateList.add(weeklyCalDate);
        }

        return calDateList;
    }

    private void setBillViewData(List<Bill> bills) {
        billTotalAmount.setText(String.format(Constants.FORMAT_BILL_BUDGET, getTotalAmount(bills)));
        billListAdapter.addAllData(bills);
        billListAdapter.notifyDataSetChanged();
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

        //billListAdapter.addData(0, bill);
        billListAdapter.notifyDataSetChanged();
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
        ownerType = OwnerType.ALL;

        final BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<List<Bill>> billObservable = billApi.viewBillByDay(
                sessionManager.getUserToken(),
                ownerType,
                String.valueOf(TimeUtils.getYearOfToday()),
                String.valueOf(TimeUtils.getMonthOfToday()),
                String.valueOf(TimeUtils.getDayOfToday())
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
        resetOwnerButton();
        llCanlendarGroup.setBackgroundResource(R.drawable.top_bg_our01);
        billTotalAmount.setTextColor(Color.parseColor("#9195B0"));
        btnOur.setTextColor(Color.parseColor("#9195B0"));

    }

    @OnClick(R.id.btnMe)
    void loadMyBill() {
        ownerType = OwnerType.MINE;

        llCanlendarGroup.setBackgroundResource(R.drawable.top_bg_me01);
        final BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<List<Bill>> billObservable = billApi.viewBillByDay(
                sessionManager.getUserToken(),
                ownerType,
                String.valueOf(TimeUtils.getYearOfToday()),
                String.valueOf(TimeUtils.getMonthOfToday()),
                String.valueOf(TimeUtils.getDayOfToday())
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
        resetOwnerButton();
        btnMe.setTextColor(Color.parseColor("#95BEC9"));
        billTotalAmount.setTextColor(Color.parseColor("#95BEC9"));

    }

    @OnClick(R.id.btnYou)
    void loadYourBill() {
        ownerType = OwnerType.PARTNER;

        final BillApi billApi = NetworkManager.getApi(BillApi.class);
        Observable<List<Bill>> billObservable = billApi.viewBillByDay(
                sessionManager.getUserToken(),
                ownerType,
                String.valueOf(TimeUtils.getYearOfToday()),
                String.valueOf(TimeUtils.getMonthOfToday()),
                String.valueOf(TimeUtils.getDayOfToday())
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
        resetOwnerButton();
        btnYou.setTextColor(Color.parseColor("#D3Bf89"));
        billTotalAmount.setTextColor(Color.parseColor("#D3Bf89"));
        llCanlendarGroup.setBackgroundResource(R.drawable.top_bg_you01);

    }

    private void resetOwnerButton() {
        btnOur.setTextColor(Color.parseColor("#444444"));
        btnYou.setTextColor(Color.parseColor("#444444"));
        btnMe.setTextColor(Color.parseColor("#444444"));
    }
}