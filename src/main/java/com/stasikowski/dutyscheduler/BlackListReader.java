package com.stasikowski.dutyscheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import com.stasikowski.dutyscheduler.entity.ScheduleInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BlackListReader {

    public static final String COMMA = ",";
    public static final String X = "x";

    public ScheduleInput processInputFile(String inputFilePath) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(inputFilePath)))) {

            Optional<String> headerLine = br.lines().findFirst();
            String[] headerRows = getHeaderRows(headerLine);
            YearMonth yearMonth = formatYearMonth(headerRows[0]);
            validateHeaderRows(headerRows, yearMonth);
            return new ScheduleInput(yearMonth, br.lines().map((line) -> {
                String[] rows = line.split(COMMA);
                Employee employee = new Employee();
                employee.setName(rows[0]);

                for (int i = 1; i < rows.length; i++) {
                    if (i == 1) {
                        employee.setMinNumberOfDuties(Integer.parseInt(rows[i]));
                    }
                    if (i == 2) {
                        employee.setMaxNumberOfDuties(Integer.parseInt(rows[i]));
                    }
                    if (X.equalsIgnoreCase(rows[i])) {
                        LocalDate day = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), i-2);
                        employee.getBlackList().add(day);
                    }
                }
                return employee;
            }).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot parse BlackList file", e);
        }
    }

    private void validateHeaderRows(String[] headerRows, YearMonth yearMonth) {
        // TODO

    }

    private String[] getHeaderRows(Optional<String> headerLine) {
        if (headerLine.isPresent()) {
            return headerLine.get().split(COMMA);
        } else {
            throw new IllegalArgumentException("BlackList file in wrong format");
        }
    }

    private YearMonth formatYearMonth(String yearMonthStr) {
        try {
            return YearMonth.parse(yearMonthStr, DateTimeFormatter.ofPattern("yyyy-MM"));
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Wrong value: '" + yearMonthStr + "' of first columnn in header. " +
                    "It should be in form of year-month for example '2017-11'");
        }
    }
}
