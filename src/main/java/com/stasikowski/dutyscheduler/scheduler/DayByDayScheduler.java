package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.DaySchedule;
import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@Deprecated
public class DayByDayScheduler implements MonthScheduler {

    @Autowired
    private CalendarHelper calendarHelper;

    @Override
    public MonthSchedule scheduleMonth(YearMonth month, List<Employee> employees) {
        List<DaySchedule> schedule = new ArrayList<>(month.lengthOfMonth());

        Map<LocalDate, List<Employee>> possibleEmployeesPerDay = calendarHelper.getPossibleEmployeesPerDay(month, employees);

        IntStream.rangeClosed(1, month.lengthOfMonth()).forEachOrdered(dayOfMonth -> {

            LocalDate day = LocalDate.of(month.getYear(), month.getMonthValue(), dayOfMonth);
            log.info("Scheduling " + day);

            Set<Employee> crew = getCrewForADay(possibleEmployeesPerDay, day);
            log.info("crew " + crew);
            boolean freeDay = calendarHelper.isFreeDay(day);
            DaySchedule daySchedule = new DaySchedule(day, freeDay, crew);
            schedule.add(daySchedule);

            if (freeDay) {
                crew.forEach(e -> e.getScheduledFreeDays().add(day));
            } else {
                crew.forEach(e -> e.getScheduledWeekDays().add(day));
            }

            List<Employee> possibleEmployeesForNextDay = possibleEmployeesPerDay.get(day.plusDays(1));
            if (possibleEmployeesForNextDay != null) {
                log.info("possibleEmployeesForNextDay " + possibleEmployeesForNextDay);
                possibleEmployeesForNextDay.removeAll(crew);
                log.info("possibleEmployeesForNextDayAfterRemoveAll " + possibleEmployeesForNextDay);
            }
        });
        return new MonthSchedule(month, schedule, employees);
    }

    private Set<Employee> getCrewForADay(Map<LocalDate, List<Employee>> possibleEmployeesPerDay, LocalDate day) {
        boolean freeDay = calendarHelper.isFreeDay(day);
        List<Employee> possibleEmployees = possibleEmployeesPerDay.get(day);
        log.info("possibleEmployees " + possibleEmployees);
        CrewCandidates crewCandidates = getPreferableEmployees(possibleEmployees, freeDay);
        log.info("crewCandidates must: " + crewCandidates.must + " possible: " + crewCandidates.possible);
        Set<Employee> crew = new HashSet<>(crewCandidates.must);
        crew.addAll(pickNRandom(crewCandidates.possible, 2 - crewCandidates.must.size()));
        return crew;
    }

    private static List<Employee> pickNRandom(List<Employee> lst, int n) {
        List<Employee> copy = new LinkedList<>(lst);
        Collections.shuffle(copy);
        return copy.subList(0, n);
    }

    private CrewCandidates getPreferableEmployees(List<Employee> possibleEmployees, boolean freeDay) {
        CrewCandidates crewCandidates = new CrewCandidates();
        ToIntFunction<? super Employee> sizeFunction = freeDay ? Employee::scheduledFreeDaysSize : Employee::scheduledWeekDaysSize;
        int min = possibleEmployees.stream().mapToInt(sizeFunction).min().getAsInt();

        possibleEmployees.sort((e1, e2) -> sizeFunction.applyAsInt(e1) - sizeFunction.applyAsInt(e2));

        Map<Integer, Set<Employee>> byCount = possibleEmployees.stream().collect(
                Collectors.groupingBy(e -> (freeDay ? (Integer) e.scheduledFreeDaysSize() : e.scheduledWeekDaysSize()),
                        Collectors.toSet()));

        Set<Employee> employeesWithMinDays = byCount.get(min);
        if (employeesWithMinDays.size() <= 2) {
            crewCandidates.must.addAll(employeesWithMinDays);
        }

        crewCandidates.possible.addAll(employeesWithMinDays);
        int currentCount = min + 1;
        while (crewCandidates.possible.size() < 2) {
            Set<Employee> byNextCount = byCount.get(currentCount);
            if (byNextCount != null) {
                crewCandidates.possible.addAll(byNextCount);
            }
            currentCount++;
        }
        return crewCandidates;
    }

    private static class CrewCandidates {
        List<Employee> must = new ArrayList<>();
        List<Employee> possible = new ArrayList<>();
    }
}
