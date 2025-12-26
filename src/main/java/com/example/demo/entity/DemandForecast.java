package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "demand_forecasts")
public class DemandForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Product product;
    
    @ManyToOne
    private Store store;
    
    private LocalDate forecastDate;
    private Integer predictedDemand;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Manual Alias for Tests
    public Integer getForecastedDemand() { return predictedDemand; }
    public void setForecastedDemand(Integer predictedDemand) { this.predictedDemand = predictedDemand; }
}