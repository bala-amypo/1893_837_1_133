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

    private final TransferSuggestionRepository suggestionRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;

    // ⚠️ DO NOT CHANGE ORDER
    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository suggestionRepo,
            InventoryLevelRepository inventoryRepo,
            DemandForecastRepository forecastRepo,
            StoreRepository storeRepo
    ) {
        this.suggestionRepo = suggestionRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.storeRepo = storeRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        List<InventoryLevel> inventoryList =
                inventoryRepo.findByProduct_Id(productId);

        if (inventoryList.isEmpty()) {
            throw new BadRequestException("No inventory found");
        }

        Product product = inventoryList.get(0).getProduct();
        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new BadRequestException("Inactive product");
        }

        Map<Store, Integer> stockMap = new HashMap<>();
        Map<Store, Integer> demandMap = new HashMap<>();

        for (InventoryLevel inv : inventoryList) {
            Store store = inv.getStore();

            List<DemandForecast> forecasts =
                    forecastRepo.findByStoreAndProductAndForecastDateAfter(
                            store,
                            product,
                            LocalDate.now()
                    );

            if (forecasts.isEmpty()) {
                throw new BadRequestException("No forecast found");
            }

            int totalForecast = forecasts.stream()
                    .mapToInt(DemandForecast::getPredictedDemand)
                    .sum();

            stockMap.put(store, inv.getQuantity());
            demandMap.put(store, totalForecast);
        }

        List<TransferSuggestion> results = new ArrayList<>();

        for (Store source : stockMap.keySet()) {
            int surplus = stockMap.get(source) - demandMap.get(source);
            if (surplus <= 0) continue;

            for (Store target : stockMap.keySet()) {
                if (source.equals(target)) continue;

                int deficit = demandMap.get(target) - stockMap.get(target);
                if (deficit <= 0) continue;

                int transferQty = Math.min(surplus, deficit);

                TransferSuggestion suggestion = new TransferSuggestion();
                suggestion.setSourceStore(source);
                suggestion.setTargetStore(target);
                suggestion.setProduct(product);
                suggestion.setQuantity(transferQty);
                suggestion.setPriority("HIGH");

                suggestionRepo.save(suggestion);
                results.add(suggestion);

                surplus -= transferQty;
                if (surplus <= 0) break;
            }
        }

        return results;
    }

    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return suggestionRepo.findBySourceStoreId(storeId);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return suggestionRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Transfer suggestion not found"));
    }
}
