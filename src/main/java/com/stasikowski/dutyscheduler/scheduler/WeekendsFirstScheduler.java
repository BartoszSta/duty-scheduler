package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.DaySchedule;
import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WeekendsFirstScheduler implements MonthScheduler{

    @Autowired
    private CalendarHelper calendarHelper;

    @Override
    public MonthSchedule scheduleMonth(YearMonth month, List<Employee> employees) {
        Map<LocalDate, DaySchedule> schedule = new TreeMap<>();
        List<LocalDate> freeDays = calendarHelper.getOrderedFreeDays(month);
        freeDays.addAll(calendarHelper.getOrderedWeekDays(month));
        Map<LocalDate, List<Employee>> possibleEmployeesPerDay = calendarHelper.getPossibleEmployeesPerDay(month, employees);

        freeDays.forEach(day -> {
            log.info("Scheduling " + day);
            Set<Employee> crew = getCrewForADay(possibleEmployeesPerDay, day);
            log.info("Selected crew for day {}: {}", day, crew);
            boolean freeDay = calendarHelper.isFreeDay(day);
            DaySchedule daySchedule = new DaySchedule(day, freeDay, crew);
            schedule.put(day, daySchedule);
            if (freeDay) {
                crew.forEach(e -> e.getScheduledFreeDays().add(day));
            } else {
                crew.forEach(e -> e.getScheduledWeekDays().add(day));
            }
            if (schedule.get(day.plusDays(1)) == null) {
                List<Employee> possibleEmployeesForNextDay = possibleEmployeesPerDay.get(day.plusDays(1));
                if (possibleEmployeesForNextDay != null) {
                    possibleEmployeesForNextDay.removeAll(crew);
                    log.info("Possible employees for next day {}", possibleEmployeesForNextDay);
                }
            }
            if (schedule.get(day.plusDays(-1)) == null) {
                List<Employee> possibleEmployeesForPreviousDay = possibleEmployeesPerDay.get(day.plusDays(-1));
                if (possibleEmployeesForPreviousDay != null) {
                    possibleEmployeesForPreviousDay.removeAll(crew);
                    log.info("Possible employees for previous day {}", possibleEmployeesForPreviousDay);
                }
            }
        });

        return new MonthSchedule(month, new ArrayList<>(schedule.values()));
    }

    private Set<Employee> getCrewForADay(Map<LocalDate, List<Employee>> possibleEmployeesPerDay, LocalDate day) {
        List<Employee> possibleEmployees = possibleEmployeesPerDay.get(day);
        possibleEmployees.removeIf(employee -> employee.scheduledDaysSize() == employee.getMaxNumberOfDuties());
        log.info("Possible employees {}", possibleEmployees);
        CrewCandidates crewCandidates = getPreferableEmployees(possibleEmployees, calendarHelper.isFreeDay(day));
        log.info("Crew candidates: {}", crewCandidates);
        Set<Employee> crew = new HashSet<>(crewCandidates.must);
        crew.addAll(pickNRandom(crewCandidates.possible, 2 - crewCandidates.must.size()));
        return crew;
    }

    private CrewCandidates getPreferableEmployees(List<Employee> possibleEmployees, boolean freeDay) {
        CrewCandidates crewCandidates = new CrewCandidates();
        ToIntFunction<? super Employee> sizeFunction = freeDay ? Employee::scheduledFreeDaysSize : Employee::scheduledDaysSize;
        int min = possibleEmployees.stream().mapToInt(sizeFunction).min().getAsInt();

        possibleEmployees.sort((e1, e2) -> sizeFunction.applyAsInt(e1) - sizeFunction.applyAsInt(e2));

        Map<Integer, Set<Employee>> byCount = possibleEmployees.stream().collect(
                Collectors.groupingBy(e -> (freeDay ? (Integer) e.scheduledFreeDaysSize() : e.scheduledDaysSize()),
                        Collectors.toSet()));

        Set<Employee> employeesWithMinDays = byCount.get(min);
        if (employeesWithMinDays.size() <= 2) {
            crewCandidates.must.addAll(employeesWithMinDays);
        } else {
            crewCandidates.possible.addAll(employeesWithMinDays);
        }
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

    @ToString
    private static class CrewCandidates {
        List<Employee> must = new ArrayList<>();
        List<Employee> possible = new ArrayList<>();
    }

    private static List<Employee> pickNRandom(List<Employee> lst, int n) {
        List<Employee> copy = new LinkedList<>(lst);
        Collections.shuffle(copy);
        return copy.subList(0, n);
    }
}
