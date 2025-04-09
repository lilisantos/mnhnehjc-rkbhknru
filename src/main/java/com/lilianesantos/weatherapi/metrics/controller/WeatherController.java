package com.lilianesantos.weatherapi.metrics.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lilianesantos.weatherapi.metrics.model.Metrics;
import com.lilianesantos.weatherapi.metrics.model.Sensor;
import com.lilianesantos.weatherapi.metrics.repository.MetricsRepoCustomImpl;
import com.lilianesantos.weatherapi.metrics.utils.WeatherUtils;
import com.lilianesantos.weatherapi.metrics.repository.MetricsRepo;
import com.lilianesantos.weatherapi.metrics.repository.SensorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private SensorRepo sensorRepo;

    @Autowired
    private MetricsRepo metricsRepo;

    @Autowired
    private WeatherUtils weatherUtils;

    private final ObjectMapper objectMapper;

    @Autowired
    public WeatherController() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    private static final Logger logger = LoggerFactory.getLogger(MetricsRepoCustomImpl.class);

    @RequestMapping(method = RequestMethod.POST, value = "/metrics")
    public List<Metrics> getMetricsData(@RequestBody CustomQuery customQuery) {

        //Validate input query
        weatherUtils.validateQuery(customQuery);

        logger.debug("Input Query: " + customQuery);

        LocalDate startDate = null;
        LocalDate endDate = null;

        // Parse dates into LocalDate objects
        if (customQuery.getDateRange() != null) {
            String startDateStr = customQuery.getDateRange().get("start");
            String endDateStr = customQuery.getDateRange().get("end");
            startDate = LocalDate.parse(startDateStr);
            endDate = LocalDate.parse(endDateStr);
        }

        return metricsRepo.findMetrics(customQuery.getSensors(), customQuery.getMetrics(), customQuery.getStatistic(), startDate, endDate);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/sensors")
    public List<Sensor> getAllSensors() {
        return sensorRepo.findAll();
    }

}
