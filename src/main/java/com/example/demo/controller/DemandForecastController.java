package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecasts")
public class DemandForecastController {

    private final DemandForecastService svc;

    public DemandForecastController(DemandForecastService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<DemandForecast> create(@RequestBody DemandForecast f) {
        return ResponseEntity.ok(svc.createForecast(f));
    }

    @GetMapping("/store/{storeId}/product/{productId}")
    public ResponseEntity<DemandForecast> get(@PathVariable Long storeId, @PathVariable Long productId) {
        return ResponseEntity.ok(svc.getForecast(storeId, productId));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<DemandForecast>> listForStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(svc.getForecastsForStore(storeId));
    }
}
