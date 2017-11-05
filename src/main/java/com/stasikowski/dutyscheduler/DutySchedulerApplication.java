package com.stasikowski.dutyscheduler;

import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.HolidayType;
import de.jollyday.ManagerParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class DutySchedulerApplication {



    public static void main(String[] args) {
//		SpringApplication.run(DutySchedulerApplication.class, args);

        YearMonth yearMonthObject = YearMonth.of(2017, 11);
        List<Employee> employees = new BlackListReader().processInputFile("C:\\Users\\Bartek\\Desktop\\blackList3.csv", yearMonthObject);
//        employees.forEach(e -> System.out.println(e.toString()));
        MonthSchedule monthSchedule = new Scheduler().scheduleMonth(yearMonthObject, employees);
        monthSchedule.getDaySchedule().forEach(s -> System.out.println(s.toString()));

        employees.forEach(e -> System.out.println(e.getStatistics()));
//        IntStream.rangeClosed(1, yearMonthObject.lengthOfMonth()).forEachOrdered(i -> {
//            LocalDate day = yearMonthObject.atDay(i);
//            System.out.println(day + " free day=" +isFreeDay(holidayManager, day));
//        });
    }


}
