package com.nexters.rainbow.rainbowcouple.calendar;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarManager {

    private static final int DAYS_OF_WEEK = 7;

    public static Date getFirstDayOfWeek() {
        LocalDate today = LocalDate.now();
        int currentWeekDay = today.getDayOfWeek();

        int dayTerm = (0 - currentWeekDay);

        return today.minusDays(dayTerm).toDate();
    }

    public static WeeklyCalDate makeWeeklyCalDate(Date monday) {
        LocalDate localDateMon = new LocalDate(monday);

        List<CalDate> dateList = new ArrayList<>();
        for (int i = 0; i < DAYS_OF_WEEK; i++) {
            LocalDate date = localDateMon.plusDays(i);
            CalDate calDate = CalDate.builder()
                    .year(date.getYear())
                    .month(date.getMonthOfYear())
                    .day(date.getDayOfMonth())
                    .date(date.toDate())
                    .build();
            dateList.add(calDate);
        }

        return WeeklyCalDate.builder()
                .year(localDateMon.getYear())
                .month(localDateMon.getMonthOfYear())
                .numberOfWeek(localDateMon.getWeekOfWeekyear())
                .weeklyCalDate(dateList)
                .build();
    }
}
