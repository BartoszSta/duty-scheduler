package com.stasikowski.dutyscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor
public final class MonthSchedule {

    private final YearMonth month;
    private final List<DaySchedule> daySchedule;
    private final List<Employee> employees;
}
