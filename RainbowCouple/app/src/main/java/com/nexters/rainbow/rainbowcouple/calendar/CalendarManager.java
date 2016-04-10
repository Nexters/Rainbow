package com.nexters.rainbow.rainbowcouple.calendar;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarManager {

    private static final int DAYS_OF_WEEK = 7;
    private static final int MAX_CALENDAR_PAGE_COUNT = 61; // must be odd

    public static Date getFirstDayOfWeek() {
        LocalDate today = LocalDate.now();
        int currentWeekDay = today.getDayOfWeek();

        int dayTerm = Math.abs(1 - currentWeekDay);

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

    public static List<WeeklyCalDate> makeCalDateList() {
        List<WeeklyCalDate> calDateList = new ArrayList<>();

        LocalDate mondayOfWeek = new LocalDate(CalendarManager.getFirstDayOfWeek());

        /* 오늘 날짜 부터 30주 전 까지 weekly date */
        int beforeWeeks = (MAX_CALENDAR_PAGE_COUNT - 1) / 2;
        for (int weekCount = beforeWeeks; weekCount > 0; weekCount--) {
            LocalDate pastMondayOfWeek = mondayOfWeek.minusWeeks(weekCount);
            WeeklyCalDate weeklyCalDate = CalendarManager.makeWeeklyCalDate(pastMondayOfWeek.toDate());
            calDateList.add(weeklyCalDate);
        }

        /* 오늘 날짜가 들어간 주간 */
        WeeklyCalDate currentWeeklyCalDate = CalendarManager.makeWeeklyCalDate(mondayOfWeek.toDate());
        calDateList.add(currentWeeklyCalDate);

        /* 오늘 날짜 부터 30주 후 까지 weekly date */
        int afterWeeks = (MAX_CALENDAR_PAGE_COUNT) / 2;
        for (int weekCount = 1; weekCount < afterWeeks; weekCount++) {
            LocalDate nextMondayOfWeek = mondayOfWeek.plusWeeks(weekCount);
            WeeklyCalDate weeklyCalDate = CalendarManager.makeWeeklyCalDate(nextMondayOfWeek.toDate());
            calDateList.add(weeklyCalDate);
        }

        return calDateList;
    }
}
