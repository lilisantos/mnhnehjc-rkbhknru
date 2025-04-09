package com.lilianesantos.weatherapi.metrics.controller;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CustomQuery {

    // CustomQuery class to hold query parameters

    private List<Integer> sensors;
    private List<String> metrics;
    private String statistic;
    @JsonDeserialize
    private Map<String, String> dateRange;

    @Override
    public String toString() {
        return "CustomQuery{" +
                "sensors=" + sensors +
                ", metrics=" + metrics +
                ", statistic='" + statistic + '\'' +
                ", dateRange=" + dateRange +
                '}';
    }

}