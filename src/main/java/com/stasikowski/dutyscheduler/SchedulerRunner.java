package com.stasikowski.dutyscheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import com.stasikowski.dutyscheduler.entity.ScheduleInput;
import com.stasikowski.dutyscheduler.exception.ScheduleNotPosibleException;
import com.stasikowski.dutyscheduler.scheduler.SchedulerValidator;
import com.stasikowski.dutyscheduler.scheduler.WeekendsFirstScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;

@Slf4j
@Component
public final class SchedulerRunner {

    public static final int MAX_RETRIES = 100;

    @Autowired
    private WeekendsFirstScheduler weekendsFirstScheduler;
    @Autowired
    private SchedulerValidator schedulerValidator;

    public MonthSchedule runSchedule( ScheduleInput scheduleInput) {
        int count = 1;
        while (true) {
            try {
                MonthSchedule monthSchedule = weekendsFirstScheduler.scheduleMonth(scheduleInput.getMonth(),
                        scheduleInput.getEmployees());
                schedulerValidator.validate(monthSchedule);
                return monthSchedule;
            } catch (ScheduleNotPosibleException e) {
                log.error("Scheduler failed attempt no " + count, e);
                if (++count > MAX_RETRIES) {
                    throw e;
                }
            }
        }
    }
}
