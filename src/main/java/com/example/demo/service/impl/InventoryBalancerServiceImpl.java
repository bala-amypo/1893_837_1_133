package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {
    
    // Exact constructor order as per PDF constraint
    private final TransferSuggestionRepository transferRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo; // Needed to check active status

    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository transferRepo,
            InventoryLevelRepository inventoryRepo,
            DemandForecastRepository forecastRepo,
            StoreRepository storeRepo,
            ProductRepository productRepo) {
        this.transferRepo = transferRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Product is inactive");
        }

        List<Store> stores = storeRepo.findAll();
        Map<Store, Integer> surplusMap = new HashMap<>();
        Map<Store, Integer> deficitMap = new HashMap<>();
        boolean forecastFound = false;

        for (Store store : stores) {
            Optional<InventoryLevel> invOpt = inventoryRepo.findByStoreAndProduct(store, product);
            int quantity = invOpt.map(InventoryLevel::getQuantity).orElse(0);

            // Fetch forecasts after today
            List<DemandForecast> forecasts = forecastRepo.findByStoreAndProductAndForecastDateAfter(store, product, LocalDate.now());
            
            if (!forecasts.isEmpty()) {
                forecastFound = true;
            }

            int totalDemand = forecasts.stream().mapToInt(DemandForecast::getForecastedDemand).sum();
            int balance = quantity - totalDemand;

            if (balance > 0) {
                surplusMap.put(store, balance);
            } else if (balance < 0) {
                deficitMap.put(store, Math.abs(balance));
            }
        }

        if (!forecastFound) {
            throw new BadRequestException("No forecast found");
        }

        List<TransferSuggestion> suggestions = new ArrayList<>();

        // Simple balancing logic: Match surpluses to deficits
        for (Map.Entry<Store, Integer> source : surplusMap.entrySet()) {
            int available = source.getValue();
            for (Map.Entry<Store, Integer> target : deficitMap.entrySet()) {
                int needed = target.getValue();
                if (available <= 0 || needed <= 0) continue;

                int transferQty = Math.min(available, needed);

                TransferSuggestion suggestion = new TransferSuggestion();
                suggestion.setSourceStore(source.getKey());
                suggestion.setTargetStore(target.getKey());
                suggestion.setProduct(product);
                suggestion.setSuggestedQuantity(transferQty);
                suggestion.setPriority("MEDIUM");
                suggestion.setReason("Balancing inventory based on forecast");
                
                suggestions.add(transferRepo.save(suggestion));

                available -= transferQty;
                target.setValue(needed - transferQty);
            }
        }
        
        return suggestions;
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}