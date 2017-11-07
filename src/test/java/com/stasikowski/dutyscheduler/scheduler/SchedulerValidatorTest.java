package com.stasikowski.dutyscheduler.scheduler;

import com.stasikowski.dutyscheduler.entity.DaySchedule;
import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.MonthSchedule;
import com.stasikowski.dutyscheduler.exception.ScheduleNotPosibleException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class SchedulerValidatorTest {

    @InjectMocks
    private SchedulerValidator schedulerValidator;

    @Test
    public void shouldNotValidateScheduleWithWrongNumberOfCrew() {
        YearMonth month = YearMonth.of(2017, 2);
        Employee e1 = createEmployee("e1", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e2 = createEmployee("e2", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e3 = createEmployee("e3", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        Employee e4 = createEmployee("e4", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        Employee e5= createEmployee("e5", 0, 16, days(month,
                new int[] {2}), Collections.emptyList());
        List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);
        List<DaySchedule> daySchedules = Arrays.asList(
                new DaySchedule(month.atDay(1), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(2), false, setOf(e3, e4, e5)),
                new DaySchedule(month.atDay(3), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(4), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(5), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(6), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(7), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(8), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(9), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(10), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(11), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(12), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(13), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(14), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(15), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(16), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(17), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(18), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(19), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(20), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(21), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(22), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(23), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(24), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(25), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(26), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(27), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(28), false, setOf(e1, e4))
        );

        try {
            schedulerValidator.validate(new MonthSchedule(month, daySchedules, employees));
            fail("Should have failed because of validatation");
        } catch (ScheduleNotPosibleException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Wrong schedule: crew size for day 2017-02-02 is different than 2");
        }
    }

    @Test
    public void shouldNotValidateScheduleWithoutAllDays() {
        YearMonth month = YearMonth.of(2017, 2);
        Employee e1 = createEmployee("e1", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e2 = createEmployee("e2", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e3 = createEmployee("e3", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24}), days(month,new int[] {4,12,18,26}));
        Employee e4 = createEmployee("e4", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24}), days(month,new int[] {4,12,18,26}));
        List<Employee> employees = Arrays.asList(e1, e2, e3, e4);
        List<DaySchedule> daySchedules = Arrays.asList(
                new DaySchedule(month.atDay(1), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(2), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(3), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(4), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(5), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(6), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(7), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(8), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(9), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(10), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(11), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(12), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(13), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(14), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(15), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(16), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(17), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(18), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(19), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(20), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(21), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(22), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(23), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(24), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(25), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(26), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(27), false, setOf(e1, e2))
        );

        try {
            schedulerValidator.validate(new MonthSchedule(month, daySchedules, employees));
            fail("Should have failed because of validatation");
        } catch (ScheduleNotPosibleException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Schedule contains wrong number of days or days from wrong month");
        }
    }

    @Test
    public void shouldNotValidateScheduleWithEmployeeDataNotMatchingSchedule() {
        YearMonth month = YearMonth.of(2017, 2);
        Employee e1 = createEmployee("e1", 13, 16, days(month,
                new int[] {1,3,7,9,13}), days(month,new int[] {5,11,19,25}));
        Employee e2 = createEmployee("e2", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e3 = createEmployee("e3", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        Employee e4 = createEmployee("e4", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        List<Employee> employees = Arrays.asList(e1, e2, e3, e4);
        List<DaySchedule> daySchedules = Arrays.asList(
                new DaySchedule(month.atDay(1), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(2), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(3), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(4), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(5), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(6), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(7), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(8), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(9), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(10), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(11), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(12), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(13), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(14), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(15), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(16), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(17), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(18), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(19), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(20), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(21), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(22), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(23), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(24), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(25), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(26), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(27), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(28), false, setOf(e3, e4))
        );

        try {
            schedulerValidator.validate(new MonthSchedule(month, daySchedules, employees));
            fail("Should have failed because of validatation");
        } catch (ScheduleNotPosibleException e) {
            Assertions.assertThat(e.getMessage()).startsWith("Wrong scheduled days for employee e1");
        }
    }

    @Test
    public void shouldNotValidateScheduleWithEmployeeDayByDayInCrew() {
        YearMonth month = YearMonth.of(2017, 2);
        Employee e1 = createEmployee("e1", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27,28}), days(month,new int[] {5,11,19,25}));
        Employee e2 = createEmployee("e2", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e3 = createEmployee("e3", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24}), days(month,new int[] {4,12,18,26}));
        Employee e4 = createEmployee("e4", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        List<Employee> employees = Arrays.asList(e1, e2, e3, e4);
        List<DaySchedule> daySchedules = Arrays.asList(
                new DaySchedule(month.atDay(1), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(2), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(3), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(4), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(5), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(6), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(7), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(8), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(9), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(10), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(11), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(12), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(13), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(14), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(15), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(16), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(17), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(18), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(19), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(20), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(21), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(22), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(23), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(24), false, setOf(e3, e4)),
                new DaySchedule(month.atDay(25), true, setOf(e1, e2)),
                new DaySchedule(month.atDay(26), true, setOf(e3, e4)),
                new DaySchedule(month.atDay(27), false, setOf(e1, e2)),
                new DaySchedule(month.atDay(28), false, setOf(e1, e4))
        );

        try {
            schedulerValidator.validate(new MonthSchedule(month, daySchedules, employees));
            fail("Should have failed because of validatation");
        } catch (ScheduleNotPosibleException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Wrong schedule: crew for day 2017-02-28 has crew mempber from previous day 2017-02-27");
        }
    }

    @Test
    public void shouldValidateValidSchedule() {
        YearMonth month = YearMonth.of(2017, 2);
        Employee e1 = createEmployee("e1", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e2 = createEmployee("e2", 13, 16, days(month,
                new int[] {1,3,7,9,13,15,17,21,23,27}), days(month,new int[] {5,11,19,25}));
        Employee e3 = createEmployee("e3", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        Employee e4 = createEmployee("e4", 13, 16, days(month,
                new int[] {2,6,8,10,14,16,20,22,24,28}), days(month,new int[] {4,12,18,26}));
        List<Employee> employees = Arrays.asList(e1, e2, e3, e4);
        List<DaySchedule> daySchedules = getValidDaySchedules(month, e1, e2, e3, e4);


        schedulerValidator.validate(new MonthSchedule(month, daySchedules, employees));
    }



    private List<DaySchedule> getValidDaySchedules(YearMonth month, Employee e1, Employee e2, Employee e3, Employee e4) {
        return Arrays.asList(
                    new DaySchedule(month.atDay(1), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(2), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(3), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(4), true, setOf(e3, e4)),
                    new DaySchedule(month.atDay(5), true, setOf(e1, e2)),
                    new DaySchedule(month.atDay(6), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(7), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(8), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(9), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(10), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(11), true, setOf(e1, e2)),
                    new DaySchedule(month.atDay(12), true, setOf(e3, e4)),
                    new DaySchedule(month.atDay(13), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(14), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(15), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(16), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(17), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(18), true, setOf(e3, e4)),
                    new DaySchedule(month.atDay(19), true, setOf(e1, e2)),
                    new DaySchedule(month.atDay(20), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(21), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(22), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(23), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(24), false, setOf(e3, e4)),
                    new DaySchedule(month.atDay(25), true, setOf(e1, e2)),
                    new DaySchedule(month.atDay(26), true, setOf(e3, e4)),
                    new DaySchedule(month.atDay(27), false, setOf(e1, e2)),
                    new DaySchedule(month.atDay(28), false, setOf(e3, e4))
            );
    }

    private Employee createEmployee(String name, int minNumberOfDuties, int maxNumberOfDuties,
                                    List<LocalDate> scheduledWeekDays, List<LocalDate> scheduledFreeDays) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setMinNumberOfDuties(minNumberOfDuties);
        employee.setMaxNumberOfDuties(maxNumberOfDuties);
        employee.setScheduledFreeDays(scheduledFreeDays);
        employee.setScheduledWeekDays(scheduledWeekDays);
        return employee;
    }

    private List<LocalDate> days(YearMonth yearMonth, int days[]) {
        List<LocalDate> result = new ArrayList<>(days.length);
        for (int day : days) {
            result.add(yearMonth.atDay(day));
        }
        return result;
    }

    private <T> Set<T> setOf(T... elements) {
        return  new HashSet<>(Arrays.asList(elements));
    }

}