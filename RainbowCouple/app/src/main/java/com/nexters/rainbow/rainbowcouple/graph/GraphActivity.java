package com.nexters.rainbow.rainbowcouple.graph;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.Bill;
import com.nexters.rainbow.rainbowcouple.bill.BillApi;
import com.nexters.rainbow.rainbowcouple.bill.OwnerType;
import com.nexters.rainbow.rainbowcouple.bill.add.BillAddActivity;
import com.nexters.rainbow.rainbowcouple.common.BaseActivity;
import com.nexters.rainbow.rainbowcouple.common.network.ExceptionHandler;
import com.nexters.rainbow.rainbowcouple.common.network.NetworkManager;
import com.nexters.rainbow.rainbowcouple.common.network.SessionManager;
import com.nexters.rainbow.rainbowcouple.common.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

//import android.support.v4.view.ViewPager;

/**
 * Created by soyoon on 2016. 1. 30..
 */
public class GraphActivity extends BaseActivity {

    private static final int ADD_BILL_ACTIVITY = 1;

    @Bind(R.id.pieChart) PieChart mChart;
    @Bind(R.id.lineChart) LineChart lineChart;
//    @Bind(R.id.calendar) ViewPager calendarView;
    @Bind(R.id.tvBtnMonthly) TextView btnMonthly;
    @Bind(R.id.tvBtnWeekly) TextView btnWeekly;
    @Bind(R.id.btnMe) Button btnMe;
    @Bind(R.id.btnYou) Button btnYou;
    @Bind(R.id.btnOur) Button btnOur;
    @Bind(R.id.rlGraphGroup) RelativeLayout rlGraphGoup;
    private OwnerType ownerType;
    private SessionManager sessionManager;
    private Date viewDate;

    protected String[] months = new String[] {
            "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"
    };

    protected String[] categories = new String[] {
            "음료", "식사", "오락", "영화", "쇼핑", "기타"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);
        sessionManager = SessionManager.getInstance(this);
        viewDate = new Date();
        ButterKnife.bind(this);
        setPieChart();
        requestStaticsData();
        setLineChart();

    }

    private void setLineChart() {
//        lineChart.setOnChartGestureListener(this);
//        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);

        // no description text
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);
        lineChart.setDrawBorders(false);

        lineChart.setBorderColor(R.color.color_our);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(2f);
//        llXAxis.enableDashedLine(5f, 5f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

//        XAxis xAxis = lineChart.getXAxis();
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

//        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
//        ll1.setLineWidth(2f);
//        ll1.enableDashedLine(5f, 5f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
//        ll1.setTypeface(tf);

//        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);
//        ll2.setTypeface(tf);

//        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
//        leftAxis.setAxisMaxValue(220f);
//        leftAxis.setAxisMinValue(0f);
//        leftAxis.setStartAtZero(false);
        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);

//        leftAxis.disableGridDashedLine();
        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setLineData(6, 100);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        lineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
//        l.setForm(Legend.LegendForm.LINE);
    }

    private void requestStaticsData() {
        final GraphApi graphApi = NetworkManager.getApi(GraphApi.class);
        Observable<List<BillStatics>> graphObservable = graphApi.viewStaticsBillByMonth(
                sessionManager.getUserToken(),
                ownerType,
                String.valueOf(TimeUtils.getYearOfDate(viewDate)),
                String.valueOf(TimeUtils.getMonthOfDate(viewDate))
        );

        bind(graphObservable)
                .subscribe(new Action1<List<BillStatics>>() {
                    @Override
                    public void call(List<BillStatics> billStatics) {
                        Log.d("BillStatics", billStatics.toString());
                        setPieData(billStatics);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("ErrorCall", "");
                    }
                });
    }

    private void setBillStaticsData(List<BillStatics> billStaticsList) {

    }

    private void setLineData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i+1) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(30f, 5f, 0f);
        set1.disableDashedLine();
//        set1.enableDashedHighlightLine(15f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(4f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//        set1.setFillDrawable(drawable);
        set1.setDrawFilled(false);
        set1.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        lineChart.setData(data);
    }

    private void setPieChart() {
        mChart.setUsePercentValues(false);
        mChart.setDescription("");
        mChart.setExtraOffsets(1, 1, 1, 1);

        mChart.setDragDecelerationFrictionCoef(0);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(getResources().getColor(R.color.color_graph_bg));
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(70f);

        mChart.setDrawCenterText(true);
        mChart.setCenterTextSize(15f);
        mChart.setCenterTextColor(getResources().getColor(R.color.color_white));

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


        Legend l = mChart.getLegend();
        l.setEnabled(false);

        // undo all highlights
        mChart.highlightValues(null);


    }

    private void setPieData(List<BillStatics> billStaticsList) {

//        long mult = amount;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        for(int i=0; i< billStaticsList.size(); i++) {
            yVals1.add(new Entry(billStaticsList.get(i).getAmount(), i));
            xVals.add(billStaticsList.get(i).getCategory());
        }

        // add a lot of colors

        colors.add(getResources().getColor(R.color.color_bill_category_drink));
        colors.add(getResources().getColor(R.color.color_bill_category_meal));
        colors.add(getResources().getColor(R.color.color_bill_category_game));
        colors.add(getResources().getColor(R.color.color_bill_category_shopping));


        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(colors);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawValues(false);


//        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setDrawValues(false);

        mChart.setNoDataText("입력된 금액이 없습니다.");
        mChart.setData(data);
        mChart.setDrawSliceText(false);

        String totalAmount = "1,356,300";
        int year = 2016;
        int month = 4;
        int week = 0;
        mChart.setCenterText("2016년 4월\n우리의 지출 총액\n"+totalAmount);

        mChart.invalidate();
    }

    @OnClick(R.id.tvBtnWeekly)
    void showWeekly() {
        btnMonthly.setTextColor(getResources().getColor(R.color.color_graph_text));
        btnMonthly.setBackgroundResource(0);
        btnWeekly.setTextColor(getResources().getColor(R.color.color_white));
        btnWeekly.setBackgroundResource(R.drawable.graph_bg_btn);
    }

    @OnClick(R.id.tvBtnMonthly)
    void showMonthly() {
        btnWeekly.setTextColor(getResources().getColor(R.color.color_graph_text));
        btnWeekly.setBackgroundResource(0);
        btnMonthly.setTextColor(getResources().getColor(R.color.color_white));
        btnMonthly.setBackgroundResource(R.drawable.graph_bg_btn);
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

//        requestBills();

        changeViewByOwnerType();
    }

    private void changeViewByOwnerType() {
        btnOur.setTextColor(getResources().getColor(R.color.owner_default));
        btnYou.setTextColor(getResources().getColor(R.color.owner_default));
        btnMe.setTextColor(getResources().getColor(R.color.owner_default));

        switch (ownerType) {
            case ALL:
                rlGraphGoup.setBackgroundResource(R.drawable.top_bg_our01);
//                billTotalAmount.setTextColor(Color.parseColor("#9195B0"));
                btnOur.setTextColor(getResources().getColor(R.color.color_our));
                break;
            case MINE:
                btnMe.setTextColor(getResources().getColor(R.color.color_me));
//                billTotalAmount.setTextColor(Color.parseColor("#95BEC9"));
                rlGraphGoup.setBackgroundResource(R.drawable.top_bg_me01);
                break;
            case PARTNER:
                btnYou.setTextColor(getResources().getColor(R.color.color_you));
//                billTotalAmount.setTextColor(Color.parseColor("#D3Bf89"));
                rlGraphGoup.setBackgroundResource(R.drawable.top_bg_you01);
                break;
        }
    }

    @OnClick(R.id.btnMain)
    void backToMain() {
        this.onBackPressed();
    }

    @OnClick(R.id.actionBtnAddBill)
    public void showBillAddDialog() {
        Intent billAddActivity = new Intent(this, BillAddActivity.class);
        startActivityForResult(billAddActivity, ADD_BILL_ACTIVITY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_BILL_ACTIVITY) {
//            requestBills();
        }
    }

}

