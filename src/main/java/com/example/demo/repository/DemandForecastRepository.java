package com.example.demo.repository;

import com.example.demo.entity.DemandForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {
    // Fix: Add this method
    List<DemandForecast> findByStore_Id(Long storeId);
}