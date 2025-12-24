package com.example.demo.service.impl;

import com.example.demo.entity.DemandForecast;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {

    private final DemandForecastRepository forecastRepo;

    public DemandForecastServiceImpl(DemandForecastRepository forecastRepo) {
        this.forecastRepo = forecastRepo;
    }

    @Override
    public DemandForecast createForecast(DemandForecast forecast) {
        if (!forecast.getForecastDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Forecast date must be in the future");
        }
        if (forecast.getPredictedDemand() < 0) {
            throw new BadRequestException("Predicted demand must be >= 0");
        }
        return forecastRepo.save(forecast);
    }

    @Override
    public List<DemandForecast> getForecastsForStore(Long storeId) {
        return forecastRepo.findByStore_Id(storeId);
    }
}
