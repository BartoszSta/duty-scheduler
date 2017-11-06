package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.DaySchedule;
import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class SchedulerValidator {

    public void validate(MonthSchedule schedule) {
        log.info("Validating schedule");
        Map<Employee, Set<LocalDate>> employeeSchedule = new HashMap<>();
        for (int i = 0; i < schedule.getDaySchedule().size(); i++) {

            DaySchedule daySchedule = schedule.getDaySchedule().get(i);
            if (daySchedule.getCrew().size() != 2) {
                throw new RuntimeException("Wrong schedule: crew size for day " + daySchedule.getDay() + " is different than 2");
            }
            if (i != 0) {
                DaySchedule previousDaySchedule = schedule.getDaySchedule().get(i - 1);
                if (!Collections.disjoint(daySchedule.getCrew(), previousDaySchedule.getCrew())) {
                    throw new RuntimeException("Wrong schedule: crew for day " + daySchedule.getDay()
                            + " has crew mempber from previous day " + previousDaySchedule.getDay());
                }
            }
            for (Employee employee : daySchedule.getCrew()) {
                Set<LocalDate> scheduledDays = employeeSchedule.get(employee);
                if (scheduledDays == null) {
                    scheduledDays = new HashSet<>();
                    employeeSchedule.put(employee, scheduledDays);
                }
                scheduledDays.add(daySchedule.getDay());
            }
        }
        employeeSchedule.forEach((employee, scheduledDays) -> {
            if (employee.getMinNumberOfDuties() > scheduledDays.size()) {
                throw new RuntimeException("Less than min duties for employee: " + employee + ", min duties: " + employee.getMinNumberOfDuties() + ", scheduled days: " + scheduledDays);
            }
            if (employee.getMaxNumberOfDuties() < scheduledDays.size()) {
                throw new RuntimeException("More than max duties for employee: " + employee + ", max duties: " + employee.getMaxNumberOfDuties() + ", scheduled days: " + scheduledDays);
            }
            Set<LocalDate> daysForEmployees = new HashSet<>(employee.getScheduledWeekDays());
            daysForEmployees.addAll(employee.getScheduledFreeDays());
            if (!daysForEmployees.equals(scheduledDays)) {
                throw new RuntimeException("Wrong scheduled days for employee: " + daysForEmployees + ", scheduled days from schedule " + scheduledDays);
            }
        });


        log.info("Schedule is valid");
    }
}
