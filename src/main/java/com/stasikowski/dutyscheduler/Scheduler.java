package com.stasikowski.dutyscheduler;

import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.HolidayType;
import de.jollyday.ManagerParameters;
import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Bartek on 2017-10-26.
 */
public class Scheduler {

    private HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(HolidayCalendar.POLAND, null));
    private static List<DayOfWeek> weekendDays = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public MonthSchedule scheduleMonth(YearMonth yearMonth, List<Employee> employees) {
        List<DaySchedule> schedule = new ArrayList<>(yearMonth.lengthOfMonth());

        Map<LocalDate, List<Employee>> possibleEmployeesPerDay = getPossibleEmployeesPerDay(yearMonth, employees);

        IntStream.rangeClosed(1, yearMonth.lengthOfMonth()).forEachOrdered(dayOfMonth -> {

            LocalDate day = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), dayOfMonth);
            System.out.println("Scheduling " + day);

            Set<Employee> crew = getCrewForADay(possibleEmployeesPerDay, day);
            System.out.println("crew " + crew);
            boolean freeDay = isFreeDay(day);
            DaySchedule daySchedule = new DaySchedule(day, freeDay, crew);
            schedule.add(daySchedule);

            if (freeDay) {
                crew.forEach(e -> e.getScheduledFreeDays().add(day));
            } else {
                crew.forEach(e -> e.getScheduledWeekDays().add(day));
            }

            List<Employee> possibleEmployeesForNextDay = possibleEmployeesPerDay.get(day.plusDays(1));
            if (possibleEmployeesForNextDay != null) {
                System.out.println("possibleEmployeesForNextDay " + possibleEmployeesForNextDay);
                possibleEmployeesForNextDay.removeAll(crew);
                System.out.println("possibleEmployeesForNextDayAfterRemoveAll " + possibleEmployeesForNextDay);
            }
        });
        return new MonthSchedule(yearMonth, schedule);
    }

    private Set<Employee> getCrewForADay(Map<LocalDate, List<Employee>> possibleEmployeesPerDay, LocalDate day) {
        boolean freeDay = isFreeDay(day);
        List<Employee> possibleEmployees = possibleEmployeesPerDay.get(day);
        System.out.println("possibleEmployees " + possibleEmployees);
        CrewCandidates crewCandidates = getPreferableEmployees(possibleEmployees, freeDay);
        System.out.println("crewCandidates must: " + crewCandidates.must + " possible: " + crewCandidates.possible);
        Set<Employee> crew = new HashSet<>(crewCandidates.must);
        crew.addAll(pickNRandom(crewCandidates.possible, 2 - crewCandidates.must.size()));
        return crew;
    }

    public static List<Employee> pickNRandom(List<Employee> lst, int n) {
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
//        for (Employee possibleEmployee : possibleEmployees) {
//            if (sizeFunction.applyAsInt(possibleEmployee) == min) {
//                result.add(possibleEmployee);
//            } else if (sizeFunction.applyAsInt(possibleEmployee) == min - 1 && result.size() < 2) {
//                result.add(possibleEmployee);
//            }
//        }

    }

    private boolean isFreeDay(LocalDate day) {
        return weekendDays.contains(day.getDayOfWeek()) || holidayManager.isHoliday(day, HolidayType.OFFICIAL_HOLIDAY);
    }

    private Map<LocalDate, List<Employee>> getPossibleEmployeesPerDay(YearMonth yearMonth, List<Employee> employees) {
        Map<LocalDate, List<Employee>> possibleEmployees = new HashMap<>();
        IntStream.rangeClosed(1, yearMonth.lengthOfMonth()).forEachOrdered(dayOfMonth -> {
            possibleEmployees.put(LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), dayOfMonth),
                    new ArrayList<>(employees));
        });
        employees.forEach(employee -> employee.getBlackLists()
                .forEach(blackedDate -> possibleEmployees.get(blackedDate).remove(employee)));
        return possibleEmployees;
    }

    private static class CrewCandidates {
        List<Employee> must = new ArrayList<>();
        List<Employee> possible = new ArrayList<>();
    }
}
