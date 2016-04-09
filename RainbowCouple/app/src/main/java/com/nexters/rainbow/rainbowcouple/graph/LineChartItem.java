package com.nexters.rainbow.rainbowcouple.graph;

import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.data.ChartData;

/**
 * Created by soyoon on 2016. 3. 26..
 */
public class LineChartItem extends ChartItem{
    public LineChartItem(ChartData<?> cd) {
        super(cd);
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {
        return null;
    }
}
