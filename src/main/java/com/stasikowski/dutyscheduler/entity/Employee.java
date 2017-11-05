package com.stasikowski.dutyscheduler.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = "name")
public class Employee {

    private String name;
    private int minNumberOfDuties;
    private int maxNumberOfDuties;
    private List<LocalDate> blackLists = new ArrayList<>();
    private List<LocalDate> scheduledWeekDays = new ArrayList<>();
    private List<LocalDate> scheduledFreeDays = new ArrayList<>();

    public int scheduledWeekDaysSize() {
        return scheduledWeekDays.size();
    }
    public int scheduledFreeDaysSize() {
        return scheduledFreeDays.size();
    }
    public int scheduledDaysSize() {
        return scheduledFreeDays.size() + scheduledWeekDays.size();
    }


    public String getStatistics() {
        return "total scheduled: " + (scheduledWeekDaysSize() + scheduledFreeDaysSize()) + ", week: "
                + scheduledWeekDaysSize() + ", free: " + scheduledFreeDaysSize() + ", days(week): " + scheduledWeekDays
                + ", days(free) " + scheduledFreeDays;
    }

    public String toString() {
        return name + "(" + scheduledWeekDaysSize() + "," + scheduledFreeDaysSize() + ")";
    }
}
