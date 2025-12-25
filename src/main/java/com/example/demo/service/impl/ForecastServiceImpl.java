package com.example.demo.service.impl;

import com.example.demo.entity.DemandForecast;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.service.ForecastService;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final DemandForecastRepository repo;

    public ForecastServiceImpl(DemandForecastRepository repo) { this.repo = repo; }

    @Override
    public DemandForecast generateForecast(DemandForecast forecast) {
        return repo.save(forecast);
    }
}