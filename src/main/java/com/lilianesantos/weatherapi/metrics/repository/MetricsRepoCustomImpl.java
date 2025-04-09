package com.lilianesantos.weatherapi.metrics.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lilianesantos.weatherapi.metrics.model.Metrics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MetricsRepoCustomImpl implements MetricsRepoCustom {

    private static final Logger logger = LoggerFactory.getLogger(MetricsRepoCustomImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final ObjectMapper objectMapper;

    @Autowired
    public MetricsRepoCustomImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public List<Metrics> findMetrics(List<Integer> sensorIds, List<String> metrics, String statistic, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Metrics> criteriaQuery = criteriaBuilder.createQuery(Metrics.class);
        Root<Metrics> root = criteriaQuery.from(Metrics.class);

        logger.debug("WeatherAPI - Sensor IDs: {}, metrics: {}, statistics: {}, startDate: {}, endDate: {}", sensorIds, metrics, statistic, startDate, endDate);

        //Set metric columns to select
        if (!metrics.isEmpty()) {
            List<Selection<?>> selections = new ArrayList<>();
            for (String metric : metrics) {
                switch (statistic){
                    case "max" -> selections.add(criteriaBuilder.max(root.get(metric)).alias(metric));
                    case "min" -> selections.add(criteriaBuilder.min(root.get(metric)).alias(metric));
                    case "sum" -> selections.add(criteriaBuilder.sum(root.get(metric)).alias(metric));
                    default -> selections.add(criteriaBuilder.avg(root.get(metric)).alias(metric));
                }
            }
            criteriaQuery.multiselect(selections);
        }

        List<Predicate> searchPredicates = new ArrayList<>();

        //Build predicate for sensorIds
        if (!sensorIds.isEmpty()) {
            searchPredicates.add(root.get("sensorId").in(sensorIds));
        }

        if (startDate != null && endDate != null) {
            //Build predicate for date range
            searchPredicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
        }

        //Add predicates to the query
        if(!searchPredicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.and(searchPredicates.toArray(new Predicate[searchPredicates.size()])));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
