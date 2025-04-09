package com.lilianesantos.weatherapi.metrics.repository;

import com.lilianesantos.weatherapi.metrics.model.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricsRepo extends JpaRepository<Metrics, Integer>, MetricsRepoCustom {


}
