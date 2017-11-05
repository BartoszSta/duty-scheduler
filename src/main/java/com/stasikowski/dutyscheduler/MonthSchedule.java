package com.stasikowski.dutyscheduler;

import lombok.Data;
import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
public class MonthSchedule {

    private final YearMonth yearMonth;
    private final List<DaySchedule> daySchedule;

    public MonthSchedule(YearMonth yearMonth, List<DaySchedule> daySchedule) {
        this.yearMonth = yearMonth;
        this.daySchedule = daySchedule;
    }

    public List<DaySchedule> getDaySchedule() {
        return daySchedule;
    }
}
