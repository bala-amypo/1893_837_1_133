package com.example.demo.service.impl;
import com.example.demo.entity.DemandForecast;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {
    private final DemandForecastRepository repo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public DemandForecastServiceImpl(DemandForecastRepository r, StoreRepository s, ProductRepository p){
        this.repo=r; this.storeRepo=s; this.productRepo=p;
    }

    public DemandForecast createForecast(DemandForecast f){
        if(f.getForecastDate().isBefore(LocalDate.now()))
            throw new BadRequestException("Forecast date must be in the future");
        return repo.save(f);
    }

    public List<DemandForecast> getForecastsForStore(Long id){
        if(!storeRepo.existsById(id)) throw new ResourceNotFoundException("Store not found");
        return repo.findByStore_Id(id);
    }

    public List<DemandForecast> findAll(){ return repo.findAll(); }
}
