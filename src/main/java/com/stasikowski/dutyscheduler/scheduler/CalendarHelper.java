package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.HolidayType;
import de.jollyday.ManagerParameters;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class CalendarHelper {

    private HolidayManager holidayManager = HolidayManager.getInstance(ManagerParameters.create(HolidayCalendar.POLAND, null));
    private static final List<DayOfWeek> weekendDays = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public boolean isFreeDay(LocalDate day) {
        return weekendDays.contains(day.getDayOfWeek()) || holidayManager.isHoliday(day, HolidayType.OFFICIAL_HOLIDAY);
    }

    public List<LocalDate> getOrderedFreeDays(YearMonth month) {
        return IntStream.rangeClosed(1, month.lengthOfMonth())
                .mapToObj(i -> LocalDate.of(month.getYear(), month.getMonthValue(), i))
                .filter(day -> isFreeDay(day)).collect(Collectors.toList());
    }

    public List<LocalDate> getOrderedWeekDays(YearMonth month) {
        return IntStream.rangeClosed(1, month.lengthOfMonth())
                .mapToObj(i -> LocalDate.of(month.getYear(), month.getMonthValue(), i))
                .filter(day -> !isFreeDay(day)).collect(Collectors.toList());
    }

    public Map<LocalDate, List<Employee>> getPossibleEmployeesPerDay(YearMonth yearMonth, List<Employee> employees) {
        Map<LocalDate, List<Employee>> possibleEmployees = new HashMap<>();
        IntStream.rangeClosed(1, yearMonth.lengthOfMonth()).forEachOrdered(dayOfMonth -> {
            possibleEmployees.put(LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), dayOfMonth),
                    new ArrayList<>(employees));
        });
        employees.forEach(employee -> employee.getBlackList()
                .forEach(blackedDate -> possibleEmployees.get(blackedDate).remove(employee)));
        return possibleEmployees;
    }
}
