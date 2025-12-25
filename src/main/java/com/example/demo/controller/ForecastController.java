package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forecasts")
public class ForecastController {
    private final DemandForecastService service;

    public ForecastController(DemandForecastService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<DemandForecast> generateForecast(@RequestBody DemandForecast forecast) {
        return ResponseEntity.ok(service.createForecast(forecast));
    }
}