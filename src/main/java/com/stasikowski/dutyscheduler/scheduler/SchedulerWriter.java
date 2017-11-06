package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.DaySchedule;
import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

@Slf4j
@Component
public class SchedulerWriter {

    @Autowired
    private CalendarHelper calendarHelper;

    public void writeSchedule(MonthSchedule monthSchedule, List<Employee> employees) {
        String fileName = "schedule-" + monthSchedule.getMonth() + "_"
                + new SimpleDateFormat("yyyyMMddkkmmsss").format(new Date()) + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"))) {

            writer.write("Scheduled month: " + monthSchedule.getMonth().toString());
            writer.newLine();
            writer.write("Day, Crew member, Crew member");
            writer.newLine();
            for (DaySchedule daySchedule : monthSchedule.getDaySchedule()) {
                List<Employee> crew = new ArrayList<>(daySchedule.getCrew());
                writer.write(daySchedule.getDay().toString() + ", " + crew.get(0).getName() + ", " + crew.get(1).getName());
                writer.newLine();
            }
            writer.newLine();
            writer.newLine();
            writer.write("Employee, Min, Max, Total, Free, Week, Days");
            writer.newLine();
            for (Employee employee : employees) {
                TreeSet<LocalDate> scheduledDays = new TreeSet<>(employee.getScheduledWeekDays());
                scheduledDays.addAll(employee.getScheduledFreeDays());

                writer.write(employee.getName() + ", " + employee.getMinNumberOfDuties() + ", "
                        + employee.getMaxNumberOfDuties() + ", " + employee.scheduledDaysSize() + ", "
                        + employee.scheduledFreeDaysSize() + ", " +  employee.scheduledWeekDaysSize() + ", ");

                for (LocalDate scheduledDay : scheduledDays) {
                    writer.write(scheduledDay.toString() + " "
                            + (calendarHelper.isFreeDay(scheduledDay) ? "(F)" : "(W)") + ", ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
