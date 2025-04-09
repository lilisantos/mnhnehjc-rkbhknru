package com.lilianesantos.weatherapi.metrics.utils;

import com.lilianesantos.weatherapi.metrics.controller.CustomQuery;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class WeatherUtils {

    private static final ArrayList<String> STATISTICS = new ArrayList<>(Arrays.asList("min", "max", "sum", "average"));
    private static final ArrayList<String> METRICS = new ArrayList<>(Arrays.asList("temperature", "humidity", "wind_speed", "precipitation"));

    public void validateQuery (CustomQuery query) {

        // Validate query is not empty
        if (query == null) {
            throw new IllegalArgumentException("Empty query");
        }

        // Validate metrics provided
        if (query.getMetrics() != null && !validateMetrics(query.getMetrics())) {
            throw new IllegalArgumentException("Invalid metrics " + query.getMetrics());
        }

        // Validate statistic provided
        if (query.getStatistic() != null && !validateStatistic(query.getStatistic())) {
            throw new IllegalArgumentException("Invalid statistic " + query.getStatistic());
        }

        // Validate date provided is between one day and one month
        if (query.getDateRange() != null){

            // Parse date range string into map
            String startDateStr = query.getDateRange().get("start");
            String endDateStr = query.getDateRange().get("end");

            if (!validateDateRange(startDateStr, endDateStr)) {
                throw new IllegalArgumentException("Invalid date range! Date range must be between one day and one month");
            }
        }
    }

    public boolean validateStatistic (String statistic) {
        return STATISTICS.stream().anyMatch(s -> s.equalsIgnoreCase(statistic));
    }

    public boolean validateMetrics (List<String> metrics) {
        return METRICS.containsAll(metrics);
    }

    public boolean validateDateRange(String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (startDateStr == null || endDateStr == null) {
            return false;
        }

        // Parse dates and validate they are between one day and month day
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);

        return daysBetween <= 1 || monthsBetween <= 1;
    }

}
