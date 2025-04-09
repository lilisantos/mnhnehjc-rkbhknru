package com.lilianesantos.weatherapi.metrics.model;


import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "metrics")
@Entity
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Avoids serialization issues with Hibernate
public class Metrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer id;
    private Integer sensorId;
    private Date date;
    private double temperature;
    private double humidity;
    private double wind_speed;
    private double precipitation;

    @Override
    public String toString() {
        return "Metrics{" +
                "id=" + id +
                ", sensorId='" + sensorId + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", wind_speed=" + wind_speed + '\'' +
                ", precipitation=" + precipitation + '\'' +
                '}';
    }
}
