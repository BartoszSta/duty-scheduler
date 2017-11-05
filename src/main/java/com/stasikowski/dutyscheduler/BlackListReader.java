package com.stasikowski.dutyscheduler;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Bartek on 2017-10-26.
 */
public class BlackListReader {

    public static final String COMMA = ",";
    public static final String X = "x";

    public List<Employee> processInputFile(String inputFilePath, YearMonth yearMonth) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFilePath))))) {

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
                    if (X.equalsIgnoreCase(rows[i])) {
                        LocalDate day = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), i);
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
