package com.nexters.rainbow.rainbowcouple.common.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Date;

/**
 * 날짜, 시간 계산용 유틸
 * Joda Time 사용
 */
public abstract class TimeUtils {

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
}
