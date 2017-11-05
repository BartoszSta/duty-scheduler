package com.stasikowski.dutyscheduler.entity;

import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
public class MonthSchedule {

    private final YearMonth month;
    private final List<DaySchedule> daySchedule;

    public MonthSchedule(YearMonth month, List<DaySchedule> daySchedule) {
        this.month = month;
        this.daySchedule = daySchedule;
    }
}
