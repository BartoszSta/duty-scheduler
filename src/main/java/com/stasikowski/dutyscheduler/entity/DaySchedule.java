package com.stasikowski.dutyscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@ToString
@Getter
@AllArgsConstructor
public final class DaySchedule {

    private final LocalDate day;
    private final boolean isFreeDay;
    private final Set<Employee> crew;

    public Set<Employee> getCrew() {
        return new HashSet<>(crew);
    }
}
