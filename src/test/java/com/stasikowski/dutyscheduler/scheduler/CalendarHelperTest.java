package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class CalendarHelperTest {

    @InjectMocks
    private CalendarHelper calendarHelper;

    @Test
    public void shouldRecognizeFreeDayForWeekend() {
        YearMonth month = YearMonth.of(2017, 02);
        for (int i = 1; i <= month.lengthOfMonth(); i++) {
            LocalDate day = month.atDay(i);
            if (day.getDayOfWeek() == DayOfWeek.SATURDAY
                    || day.getDayOfWeek() == DayOfWeek.SUNDAY) {
                assertTrue(calendarHelper.isFreeDay(day));
            } else {
                assertFalse(calendarHelper.isFreeDay(day));
            }
        }
    }

    @Test
    public void shouldRecognizeFreeDayForPublicHolidays() {
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 1, 1)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 1, 6)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 4, 17)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 5, 1)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 5, 3)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 6, 4)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 6, 15)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 8, 15)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 11, 1)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 11, 12)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 12, 25)));
        assertTrue(calendarHelper.isFreeDay(LocalDate.of(2017, 12, 26)));
    }

    @Test
    public void shouldReturnCorrectlyFreeDaysForMonth() {
        List<LocalDate> orderedFreeDays = calendarHelper.getOrderedFreeDays(YearMonth.of(2017, 12));
        assertThat(orderedFreeDays).containsExactly(
                LocalDate.of(2017, 12, 2),
                LocalDate.of(2017, 12, 3),
                LocalDate.of(2017, 12, 9),
                LocalDate.of(2017, 12, 10),
                LocalDate.of(2017, 12, 16),
                LocalDate.of(2017, 12, 17),
                LocalDate.of(2017, 12, 23),
                LocalDate.of(2017, 12, 24),
                LocalDate.of(2017, 12, 25),
                LocalDate.of(2017, 12, 26),
                LocalDate.of(2017, 12, 30),
                LocalDate.of(2017, 12, 31));
    }

    @Test
    public void shouldReturnCorrectlyWeekDaysForMonth() {
        List<LocalDate> orderedFreeDays = calendarHelper.getOrderedWeekDays(YearMonth.of(2017, 12));
        assertThat(orderedFreeDays).containsExactly(
                LocalDate.of(2017, 12, 1),
                LocalDate.of(2017, 12, 4),
                LocalDate.of(2017, 12, 5),
                LocalDate.of(2017, 12, 6),
                LocalDate.of(2017, 12, 7),
                LocalDate.of(2017, 12, 8),
                LocalDate.of(2017, 12, 11),
                LocalDate.of(2017, 12, 12),
                LocalDate.of(2017, 12, 13),
                LocalDate.of(2017, 12, 14),
                LocalDate.of(2017, 12, 15),
                LocalDate.of(2017, 12, 18),
                LocalDate.of(2017, 12, 19),
                LocalDate.of(2017, 12, 20),
                LocalDate.of(2017, 12, 21),
                LocalDate.of(2017, 12, 22),
                LocalDate.of(2017, 12, 27),
                LocalDate.of(2017, 12, 28),
                LocalDate.of(2017, 12, 29));
    }

    @Test
    public void shouldGetPossibleEmployeesPerDay() {
        Employee employee1 = createEmployee("e1", Arrays.asList(LocalDate.of(2017, 12, 1)));
        Employee employee2 = createEmployee("e2", Arrays.asList(LocalDate.of(2017, 12, 1),
                LocalDate.of(2017, 12, 2)));
        Map<LocalDate, List<Employee>> possibleEmployeesPerDay = calendarHelper.getPossibleEmployeesPerDay(
                YearMonth.of(2017, 12), Arrays.asList(employee1, employee2));
        assertThat(possibleEmployeesPerDay.get(LocalDate.of(2017, 12, 1))).isEmpty();
        assertThat(possibleEmployeesPerDay.get(LocalDate.of(2017, 12, 2))).containsExactly(employee1);
        assertThat(possibleEmployeesPerDay.get(LocalDate.of(2017, 12, 3))).containsExactly(employee1, employee2);
    }

    private Employee createEmployee(String name, List<LocalDate> blackList) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setBlackList(blackList);
        return employee;
    }
}