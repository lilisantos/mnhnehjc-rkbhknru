package com.lilianesantos.weatherapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilianesantos.weatherapi.metrics.controller.CustomQuery;
import com.lilianesantos.weatherapi.metrics.controller.WeatherController;
import com.lilianesantos.weatherapi.metrics.utils.WeatherUtils;
import com.lilianesantos.weatherapi.metrics.repository.MetricsRepo;
import com.lilianesantos.weatherapi.metrics.repository.SensorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;
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

    @InjectMocks
    private WeatherController weatherController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void getMetricsData_validQuery() throws Exception {
        mockMvc.perform(post("/weather/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sensors\": [1], \"metrics\": [\"temperature\", \"humidity\"], \"date-range\": {\"start\": \"2025-04-02\", \"end\": \"2025-04-09\"}, \"statistic\": \"average\"}"))
                .andExpect(status().isOk());
    }

}
