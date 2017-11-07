package com.stasikowski.dutyscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public final class ScheduleInput {
    private final YearMonth month;
    private final List<Employee> employees;

    public List<Employee> getEmployees() {
        employees.forEach(employee -> employee.clearSchedule());
        return new ArrayList<>(employees);
    }
}
