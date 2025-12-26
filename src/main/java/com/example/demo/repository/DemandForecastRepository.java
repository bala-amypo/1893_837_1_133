package com.example.demo.repository;
import com.example.demo.entity.DemandForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {}