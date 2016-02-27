package com.nexters.rainbow.rainbowcouple.calendar;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class WeeklyCalDate {
    private int year;
    private int month;
    private int numberOfWeek;
    private List<CalDate> weeklyCalDate;
}
