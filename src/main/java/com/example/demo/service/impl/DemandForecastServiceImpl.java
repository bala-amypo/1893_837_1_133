package com.example.demo.service.impl;

import com.example.demo.entity.DemandForecast;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {

    private final DemandForecastRepository repo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public DemandForecastServiceImpl(DemandForecastRepository repo, StoreRepository storeRepo, ProductRepository productRepo) {
        this.repo = repo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    @Override
    public DemandForecast createForecast(DemandForecast forecast) {
        if (forecast.getForecastDate() == null || !forecast.getForecastDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Forecast date must be in the future");
        }
        if (forecast.getForecastedDemand() == null || forecast.getForecastedDemand() < 0) {
            throw new BadRequestException("Predicted demand must be >= 0");
        }
        Store s = storeRepo.findById(forecast.getStore().getId()).orElseThrow(() -> new BadRequestException("Store not found"));
        Product p = productRepo.findById(forecast.getProduct().getId()).orElseThrow(() -> new BadRequestException("Product not found"));
        forecast.setStore(s);
        forecast.setProduct(p);
        return repo.save(forecast);
    }

    @Override
    public List<DemandForecast> getForecastsForStore(Long storeId) {
        return repo.findByStore_Id(storeId);
    }

    @Override
    public DemandForecast getForecast(Long storeId, Long productId) {
        // Return latest future forecast if exists
        Store s = storeRepo.findById(storeId).orElseThrow(() -> new BadRequestException("Store not found"));
        Product p = productRepo.findById(productId).orElseThrow(() -> new BadRequestException("Product not found"));
        List<DemandForecast> list = repo.findByStoreAndProductAndForecastDateAfter(s, p, LocalDate.now());
        return list.isEmpty() ? null : list.get(0);
    }
}
