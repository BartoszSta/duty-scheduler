package com.stasikowski.dutyscheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import com.stasikowski.dutyscheduler.scheduler.DayByDayScheduler;
import com.stasikowski.dutyscheduler.scheduler.SchedulerValidator;
import com.stasikowski.dutyscheduler.scheduler.WeekendsFirstScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class DutySchedulerApplication implements CommandLineRunner {

    @Autowired
    private WeekendsFirstScheduler weekendsFirstScheduler;

    @Autowired
    private BlackListReader blackListReader;

    @Autowired
    private SchedulerValidator schedulerValidator;

    public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DutySchedulerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please run application with 2 arguments year and month for example: 2018 2");
        }
        log.info("Starting application with arguments {}", Arrays.toString(args));
        YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//        List<Employee> employees = blackListReader.processInputFile("C:\\Users\\Bartek\\Desktop\\blackList4.csv", yearMonthObject);
        List<Employee> employees = blackListReader.processInputFile("blackList.csv", yearMonthObject);
//        employees.forEach(e -> log.info(e.toString()));

//        MonthSchedule monthSchedule = new DayByDayScheduler().scheduleMonth(yearMonthObject, employees);
//        monthSchedule.getDaySchedule().forEach(s -> log.info(s.toString()));

        MonthSchedule monthSchedule2  = weekendsFirstScheduler.scheduleMonth(yearMonthObject, employees);
        monthSchedule2.getDaySchedule().forEach(s -> log.info(s.toString()));
        employees.forEach(e -> log.info(e.getStatistics()));

        schedulerValidator.validate(monthSchedule2);
    }
}
