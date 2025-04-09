package com.lilianesantos.weatherapi.metrics.repository;

import com.lilianesantos.weatherapi.metrics.model.Metrics;
import jakarta.persistence.Tuple;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

// This is a custom repository interface for the Metrics entity.
// It can be used to define custom query methods or other repository-related logic.
@Repository
public interface MetricsRepoCustom {

    List<Metrics> findMetrics(List<Integer> sensorIds, List<String> metrics, String statistic, LocalDate startDate, LocalDate endDate);

}
