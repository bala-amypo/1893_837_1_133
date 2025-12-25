package com.example.demo.service;
import com.example.demo.entity.DemandForecast;

public interface ForecastService {
    DemandForecast generateForecast(DemandForecast forecast);
}