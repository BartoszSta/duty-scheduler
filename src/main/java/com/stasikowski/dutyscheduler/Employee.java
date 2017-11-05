package com.stasikowski.dutyscheduler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(of = "name")
public class Employee {

    String name;
    List<LocalDate> blackLists = new ArrayList<>();
    List<LocalDate> scheduledWeekDays = new ArrayList<>();
    List<LocalDate> scheduledFreeDays = new ArrayList<>();

    public int scheduledWeekDaysSize() {
        return scheduledWeekDays.size();
    }
    public int scheduledFreeDaysSize() {
        return scheduledFreeDays.size();
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
