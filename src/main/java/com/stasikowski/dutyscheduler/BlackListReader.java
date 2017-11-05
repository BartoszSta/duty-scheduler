package com.stasikowski.dutyscheduler;

import com.stasikowski.dutyscheduler.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BlackListReader {

    public static final String COMMA = ",";
    public static final String X = "x";

    public List<Employee> processInputFile(String inputFilePath, YearMonth yearMonth) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(inputFilePath)))) {

            Optional<String> headerLine = br.lines().findFirst();
            if (headerLine.isPresent()) {
                String[] rows = headerLine.get().split(COMMA);
                //TODO validate
            } else {
                throw new IllegalArgumentException("BlackList file in wrong format");
            }
            return br.lines().map((line) -> {
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
                        employee.getBlackLists().add(day);
                    }
                }
                return employee;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot parse BlackList file", e);
        }
    }
}
