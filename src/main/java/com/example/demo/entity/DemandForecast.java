package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demand_forecast")
public class DemandForecast {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Store store;

    private LocalDate forecastDate;
    private Integer forecastedDemand;
    private Double confidenceScore;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }
    public Integer getForecastedDemand() { return forecastedDemand; }
    public void setForecastedDemand(Integer forecastedDemand) { this.forecastedDemand = forecastedDemand; }
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
}
