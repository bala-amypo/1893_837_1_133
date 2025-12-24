package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecasts")
public class DemandForecastController {

    private final DemandForecastService forecastService;

    public DemandForecastController(DemandForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping
    public DemandForecast create(@RequestBody DemandForecast forecast) {
        return forecastService.createForecast(forecast);
    }

    @GetMapping("/store/{storeId}")
    public List<DemandForecast> getForStore(@PathVariable Long storeId) {
        return forecastService.getForecastsForStore(storeId);
    }
}
