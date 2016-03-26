package com.nexters.rainbow.rainbowcouple.common.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 날짜, 시간 계산용 유틸
 * Joda Time 사용
 */
public abstract class TimeUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    public static Long getDateTime(Date date) {
        return date.getTime();
    }

    public static Date getToday() {
        return LocalDateTime.now().toDate();
    }

    public static Date getDateTimeBeforeDays(int beforeDays) {
        return LocalDateTime.now().minusDays(beforeDays).toDate();
    }
    public static Date getDateTimeAfterDays(int afterDays) {
        return LocalDateTime.now().plusDays(afterDays).toDate();
    }

    public static Date getDateBeforeDays(int beforeDays) {
        return LocalDate.now().minusDays(beforeDays).toDate();
    }
    public static Date getDateAfterDays(int afterDays) {
        return LocalDate.now().plusDays(afterDays).toDate();
    }

    public static int getYearOfToday() {
        return LocalDate.now().getYear();
    }

    public static int getMonthOfToday() {
        return LocalDate.now().getMonthOfYear();
    }

    public static int getDayOfToday() {
        return LocalDate.now().getDayOfMonth();
    }

    public static int getYearOfDate(Date date) {
        return LocalDate.fromDateFields(date).getYear();
    }

    public static int getMonthOfDate(Date date) {
        return LocalDate.fromDateFields(date).getMonthOfYear();
    }

    public static int getDayOfDate(Date date) {
        return LocalDate.fromDateFields(date).getDayOfMonth();
    }

    public static String getTodayToString() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(getToday());
    }

    public static String getDateToString(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    public static Date getDateFromMillis(Long millisecond) {
        return new LocalDateTime(millisecond).toDate();
    }
}
