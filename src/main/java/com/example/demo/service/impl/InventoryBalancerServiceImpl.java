package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {
    private final ProductRepository productRepo;
    private final InventoryLevelRepository invRepo;
    private final DemandForecastRepository forecastRepo;
    private final TransferSuggestionRepository tsRepo;

    public InventoryBalancerServiceImpl(ProductRepository p, InventoryLevelRepository i, DemandForecastRepository f, TransferSuggestionRepository t){
        this.productRepo=p; this.invRepo=i; this.forecastRepo=f; this.tsRepo=t;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId){
        Product p = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if(!p.isActive())
            throw new BadRequestException("Inactive product");

        List<InventoryLevel> inventories = invRepo.findByProduct_Id(productId);
        List<DemandForecast> forecasts = forecastRepo.findByProduct_Id(productId);
        if(inventories.size()<2 || forecasts.isEmpty()) return List.of();

        Store src = inventories.get(0).getStore();
        Store tgt = inventories.get(1).getStore();
        Integer q1 = inventories.get(0).getQuantity();
        Integer q2 = inventories.get(1).getQuantity();
        Integer f1 = forecasts.get(0).getForecastedDemand();
        Integer f2 = forecasts.get(1).getForecastedDemand();

        if(q1>f1 && q2<f2){
            TransferSuggestion ts = new TransferSuggestion();
            ts.setProduct(p);
            ts.setSourceStore(src);
            ts.setTargetStore(tgt);
            ts.setSuggestedQuantity(Math.min(q1-f1, f2-q2));
            ts.setReason("Imbalance detected");
            tsRepo.save(ts);
        }
        return tsRepo.findByProduct_Id(productId);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id){
        return tsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
