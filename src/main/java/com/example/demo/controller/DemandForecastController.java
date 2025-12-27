package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forecasts")
public class DemandForecastController {
    private final DemandForecastService service;
    public DemandForecastController(DemandForecastService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<DemandForecast> create(@RequestBody DemandForecast f) {
        return ResponseEntity.ok(service.createForecast(f));
    }
}