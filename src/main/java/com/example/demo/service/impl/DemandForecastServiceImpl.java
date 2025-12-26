package com.example.demo.service;

import com.example.demo.entity.DemandForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.repository.StoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DemandForecastServiceImpl implements DemandForecastService {
    private final DemandForecastRepository repo;
    private final StoreRepository storeRepo;
    public DemandForecastServiceImpl(DemandForecastRepository r, StoreRepository s){
        this.repo=r; this.storeRepo=s;
    }

    @Override
    public DemandForecast createForecast(DemandForecast f){
        if(f.getForecastDate()!=null && f.getForecastDate().isBefore(LocalDate.now()))
            throw new BadRequestException("Past forecast date not allowed");
        return repo.save(f);
    }

    @Override
    public List<DemandForecast> getForecastsForStore(Long storeId){
        if(!storeRepo.existsById(storeId))
            throw new ResourceNotFoundException("Store not found");
        return repo.findByStore_Id(storeId);
    }

}
