package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.DaySchedule;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class SchedulerValidator {

    public void validate(MonthSchedule schedule) {
        log.info("Validating schedule");
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
        }
        log.info("Schedule is valid");
    }
}
