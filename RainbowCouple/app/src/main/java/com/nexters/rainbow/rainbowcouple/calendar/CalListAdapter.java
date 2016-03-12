package com.nexters.rainbow.rainbowcouple.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexters.rainbow.rainbowcouple.R;
import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalListAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int layoutResource;
    private List<WeeklyCalDate> dataList;

    private CalendarItemSelectedListener selectedListener = null;

    public interface CalendarItemSelectedListener {
        void selectDate(CalDate date);
    }

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

    public void setSelectedListener(CalendarItemSelectedListener listener) {
        this.selectedListener = listener;
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
        List<CalDate> calDateList = weeklyCalDate.getWeeklyCalDate();

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 view는 layoutResource로 외부에서 주입 받아 사용
        View view = inflater.inflate(layoutResource, null);

        CalDateItemViewHolder viewHolder = bindView(view);
        viewHolder.textViewYear.setText(String.valueOf(weeklyCalDate.getYear()));
        viewHolder.textViewMonth.setText(String.valueOf(weeklyCalDate.getMonth()));

        setTextViewByCalDate(viewHolder, calDateList);

        setLayoutTagByCalDate(viewHolder, calDateList);

        container.addView(view);

        return view;
    }

    private void setTextViewByCalDate(CalDateItemViewHolder viewHolder, List<CalDate> calDateList) {
        viewHolder.textViewMon.setText(String.valueOf(calDateList.get(0).getDay()));
        viewHolder.textViewTue.setText(String.valueOf(calDateList.get(1).getDay()));
        viewHolder.textViewWed.setText(String.valueOf(calDateList.get(2).getDay()));
        viewHolder.textViewThur.setText(String.valueOf(calDateList.get(3).getDay()));
        viewHolder.textViewFri.setText(String.valueOf(calDateList.get(4).getDay()));
        viewHolder.textViewSat.setText(String.valueOf(calDateList.get(5).getDay()));
        viewHolder.textViewSun.setText(String.valueOf(calDateList.get(6).getDay()));
    }

    private void setLayoutTagByCalDate(CalDateItemViewHolder viewHolder, List<CalDate> calDateList) {
        viewHolder.layoutMon.setTag(calDateList.get(0));
        viewHolder.layoutTue.setTag(calDateList.get(1));
        viewHolder.layoutWed.setTag(calDateList.get(2));
        viewHolder.layoutThur.setTag(calDateList.get(3));
        viewHolder.layoutFri.setTag(calDateList.get(4));
        viewHolder.layoutSat.setTag(calDateList.get(5));
        viewHolder.layoutSun.setTag(calDateList.get(6));
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

        @Bind(R.id.layoutMon) RelativeLayout layoutMon;
        @Bind(R.id.layoutTue) RelativeLayout layoutTue;
        @Bind(R.id.layoutWed) RelativeLayout layoutWed;
        @Bind(R.id.layoutThur) RelativeLayout layoutThur;
        @Bind(R.id.layoutFri) RelativeLayout layoutFri;
        @Bind(R.id.layoutSat) RelativeLayout layoutSat;
        @Bind(R.id.layoutSun) RelativeLayout layoutSun;

        @OnClick({R.id.layoutMon, R.id.layoutTue, R.id.layoutWed, R.id.layoutThur,
                  R.id.layoutFri, R.id.layoutSat, R.id.layoutSun})
        public void onClickDay(RelativeLayout layoutOfDay) {
            if (ObjectUtils.isEmpty(selectedListener)) {
                return;
            }

            CalDate calDate = (CalDate) layoutOfDay.getTag();
            selectedListener.selectDate((CalDate) layoutOfDay.getTag());
        }

        CalDateItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}