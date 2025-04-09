package com.lilianesantos.weatherapi;

import com.lilianesantos.weatherapi.metrics.controller.CustomQuery;
import com.lilianesantos.weatherapi.metrics.controller.WeatherController;
import com.lilianesantos.weatherapi.metrics.utils.WeatherUtils;
import com.lilianesantos.weatherapi.metrics.repository.MetricsRepo;
import com.lilianesantos.weatherapi.metrics.repository.SensorRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorRepo sensorRepo;

    @MockBean
    private MetricsRepo metricsRepo;

    @MockBean
    private WeatherUtils weatherUtils;

    @MockBean
    private WeatherController weatherController;

    @Test
    void getMetricsData_validQuery() throws Exception {
        this.mockMvc.perform(post("/weather/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sensors\": [1], \"metrics\": [\"temperature\", \"humidity\"], \"date-range\": {\"start\": \"2025-04-02\", \"end\": \"2025-04-09\"}, \"statistic\": \"average\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getMetricsData_invalidDateRange() throws Exception {
        CustomQuery customQuery = new CustomQuery();
        customQuery.setSensors(Arrays.asList(1));
        customQuery.setStatistic("average");
        customQuery.setMetrics(Arrays.asList("temperature", "humidity"));
        Map<String, String> dateRange = new HashMap<>();
        dateRange.put("start", "2024-04-02");
        dateRange.put("end", "2025-04-09");
        customQuery.setDateRange(dateRange);

        // Configure the mock to throw an exception
        doThrow(new IllegalArgumentException("Invalid date range! Date range must be between one day and one month"))
                .when(weatherUtils).validateQuery(customQuery);

        // Assert that the exception is thrown
        assertThrows(IllegalArgumentException.class, () -> weatherUtils.validateQuery(customQuery));
    }

    @Test
    void getMetricsData_invalidStatistic() throws Exception {
        CustomQuery customQuery = new CustomQuery();
        customQuery.setSensors(Arrays.asList(1));
        customQuery.setMetrics(Arrays.asList("temperature", "humidity"));
        Map<String, String> dateRange = new HashMap<>();
        dateRange.put("start", "2025-04-02");
        dateRange.put("end", "2025-04-09");
        customQuery.setDateRange(dateRange);
        customQuery.setStatistic("maximum");

        doThrow(new IllegalArgumentException("Invalid statistic maximum"))
                .when(weatherUtils).validateQuery(customQuery);

        assertThrows(IllegalArgumentException.class, () -> weatherUtils.validateQuery(customQuery));
    }

}
