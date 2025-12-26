package com.example.demo.service.impl;
import com.example.demo.entity.TransferSuggestion;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {
    private final TransferSuggestionRepository repo;
    private final InventoryLevelRepository invRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;

    public InventoryBalancerServiceImpl(TransferSuggestionRepository r, InventoryLevelRepository i, DemandForecastRepository f, StoreRepository s){
        this.repo=r; this.invRepo=i; this.forecastRepo=f; this.storeRepo=s;
    }

    public List<TransferSuggestion> generateSuggestions(Long id){
        if(repo.findByProduct_Id(id).isEmpty())
            throw new ResourceNotFoundException("Suggestion not found");
        return repo.findByProduct_Id(id);
    }

    public TransferSuggestion getSuggestionById(Long id){
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
