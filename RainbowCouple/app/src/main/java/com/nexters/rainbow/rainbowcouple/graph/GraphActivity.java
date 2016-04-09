package com.nexters.rainbow.rainbowcouple.graph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.bill.OwnerType;
import com.nexters.rainbow.rainbowcouple.bill.add.BillAddActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by soyoon on 2016. 1. 30..
 */
public class GraphActivity extends FragmentActivity {

    private static final int ADD_BILL_ACTIVITY = 1;

    @Bind(R.id.chart1) PieChart mChart;
    @Bind(R.id.calendar) ViewPager calendarView;
    @Bind(R.id.tvBtnMonthly) TextView btnMonthly;
    @Bind(R.id.tvBtnWeekly) TextView btnWeekly;
    @Bind(R.id.btnMe) Button btnMe;
    @Bind(R.id.btnYou) Button btnYou;
    @Bind(R.id.btnOur) Button btnOur;
    @Bind(R.id.rlGraphGroup) RelativeLayout rlGraphGoup;
    private OwnerType ownerType;

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
        ButterKnife.bind(this);
        setChart();
        setData(3, 100);

    }

    private void setChart() {
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
        String amount = "1,356,300";
        mChart.setCenterText("12.01 ~ 12.13\n우리의 지출 총액\n"+amount);
        mChart.setCenterTextColor(getResources().getColor(R.color.color_white));

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


    }

    private void setData(int count, long amount) {

        long mult = amount;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        yVals1.add(new Entry((long) 350000 , 0));
        yVals1.add(new Entry((long) 200000 , 0));
        yVals1.add(new Entry((long) 80000 , 0));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("음료");
        xVals.add("식사");
        xVals.add("게임");


        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

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

        Legend l = mChart.getLegend();
        l.setEnabled(false);

        // undo all highlights
        mChart.highlightValues(null);

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

