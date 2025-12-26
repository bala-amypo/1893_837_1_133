package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "demand_forecasts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandForecast {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    
    @Column(nullable = false)
    private LocalDate forecastDate;
    
    @Column(nullable = false)
    private Integer forecastedDemand;
    
    @Column
    private Double confidenceScore;
}