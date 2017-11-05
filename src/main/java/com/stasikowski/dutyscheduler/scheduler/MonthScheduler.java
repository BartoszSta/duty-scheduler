package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;

import java.time.YearMonth;
import java.util.List;

public interface MonthScheduler {

    MonthSchedule scheduleMonth(YearMonth month, List<Employee> employees);
}
