package com.lilianesantos.weatherapi.metrics.repository;

import com.lilianesantos.weatherapi.metrics.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepo extends JpaRepository<Sensor, Integer> {

}
