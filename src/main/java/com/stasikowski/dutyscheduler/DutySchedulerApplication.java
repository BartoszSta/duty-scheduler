package com.stasikowski.dutyscheduler;

import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import com.stasikowski.dutyscheduler.entity.ScheduleInput;
import com.stasikowski.dutyscheduler.scheduler.SchedulerWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DutySchedulerApplication implements CommandLineRunner {

    @Autowired
    private BlackListReader blackListReader;
    @Autowired
    private SchedulerRunner schedulerRunner;
    @Autowired
    private SchedulerWriter schedulerWriter;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DutySchedulerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        ScheduleInput scheduleInput = blackListReader.processInputFile("scheduleInput.csv");
        MonthSchedule monthSchedule = schedulerRunner.runSchedule(scheduleInput);
        monthSchedule.getDaySchedule().forEach(s -> log.info(s.toString()));
        monthSchedule.getEmployees().forEach(e -> log.info(e.getStatistics()));
        schedulerWriter.writeSchedule(monthSchedule);
    }
}
