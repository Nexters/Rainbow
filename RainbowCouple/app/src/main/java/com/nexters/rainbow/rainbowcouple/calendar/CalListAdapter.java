package com.nexters.rainbow.rainbowcouple.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalListAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int layoutResource;
    private List<WeeklyCalDate> dataList;

    public CalListAdapter(Context context, int layoutResourceId) {
        this.context = context;
        this.layoutResource = layoutResourceId;
        this.inflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public CalListAdapter(Context context, int layoutResourceId, List<WeeklyCalDate> dataList) {
        this.context = context;
        this.layoutResource = layoutResourceId;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
     * 쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
     *
     * @param container   : ViewPager
     * @param position : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        WeeklyCalDate weeklyCalDate = dataList.get(position);

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
        View view = inflater.inflate(layoutResource, null);

        CalDateItemViewHolder viewHolder = bindView(view);

        viewHolder.textViewYear.setText(String.valueOf(weeklyCalDate.getYear()));
        viewHolder.textViewMonth.setText(String.valueOf(weeklyCalDate.getMonth()));

        List<CalDate> calDateList = weeklyCalDate.getWeeklyCalDate();
        viewHolder.textViewMon.setText(String.valueOf(calDateList.get(0).getDay()));
        viewHolder.textViewTue.setText(String.valueOf(calDateList.get(1).getDay()));
        viewHolder.textViewWed.setText(String.valueOf(calDateList.get(2).getDay()));
        viewHolder.textViewThur.setText(String.valueOf(calDateList.get(3).getDay()));
        viewHolder.textViewFri.setText(String.valueOf(calDateList.get(4).getDay()));
        viewHolder.textViewSat.setText(String.valueOf(calDateList.get(5).getDay()));
        viewHolder.textViewSun.setText(String.valueOf(calDateList.get(6).getDay()));

        container.addView(view);

        return view;
    }

    /**
     * 화면에 보이지 않은 View를 제거하여 메모리 관리.
     *
     * @param container : ViewPager
     * @param position  : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
     * @param object    : 파괴될 객체(더 이상 보이지 않은 View 객체)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public CalDateItemViewHolder bindView(View parentView) {
        return new CalDateItemViewHolder(parentView);
    }

    /**
     * view 100개를 생성한 뒤 무한 스크롤 효과
     */
    @Override
    public int getCount() {
        return 60;
    }

    /**
     * instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    class CalDateItemViewHolder {
        @Bind(R.id.textViewYear) TextView textViewYear;
        @Bind(R.id.textViewMonth) TextView textViewMonth;
        @Bind(R.id.textViewSun) TextView textViewSun;
        @Bind(R.id.textViewMon) TextView textViewMon;
        @Bind(R.id.textViewTue) TextView textViewTue;
        @Bind(R.id.textViewWed) TextView textViewWed;
        @Bind(R.id.textViewThur) TextView textViewThur;
        @Bind(R.id.textViewFri) TextView textViewFri;
        @Bind(R.id.textViewSat) TextView textViewSat;

        CalDateItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}