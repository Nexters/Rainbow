package com.nexters.rainbow.rainbowcouple.graph;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by soyoon on 2016. 1. 30..
 */
public class GraphActivity extends DemoBase {

    @Bind(R.id.chart1) PieChart mChart;
    @Bind(R.id.calendar) ViewPager calendarView;


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
        mChart.setHoleRadius(65f);

        mChart.setDrawCenterText(true);
        String amount = "1,356,300";
        mChart.setCenterText("우리의 지출 총액\n"+amount);
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
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((long) (Math.random() * mult) , i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++) {
            xVals.add(mParties[i % mParties.length]);

        }


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

}

