package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final TransferSuggestionRepository transferRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    // Constructor order required by tests
    public InventoryBalancerServiceImpl(TransferSuggestionRepository transferRepo,
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
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (!product.isActive()) {
            throw new BadRequestException("Product is inactive");
        }

        List<InventoryLevel> inventories = inventoryRepo.findByProduct_Id(productId);
        if (inventories.isEmpty()) {
            throw new BadRequestException("No forecast found");
        }

        // For each store, get future forecasts
        Map<Long, Integer> forecastMap = new HashMap<>();
        for (InventoryLevel inv : inventories) {
            List<DemandForecast> f = forecastRepo.findByStoreAndProductAndForecastDateAfter(inv.getStore(), product, LocalDate.now());
            if (f.isEmpty()) {
                // If any store has no forecast, throw as per spec
                throw new BadRequestException("No forecast found");
            }
            // sum predicted demand for that store
            int sum = f.stream().mapToInt(DemandForecast::getForecastedDemand).sum();
            forecastMap.put(inv.getStore().getId(), sum);
        }

        // Simple balancing: compute average demand and move from stores with inventory > demand to those with inventory < demand
        List<TransferSuggestion> suggestions = new ArrayList<>();
        Map<Long, Integer> inventoryMap = inventories.stream().collect(Collectors.toMap(i -> i.getStore().getId(), InventoryLevel::getQuantity));

        int totalInventory = inventoryMap.values().stream().mapToInt(Integer::intValue).sum();
        int totalForecast = forecastMap.values().stream().mapToInt(Integer::intValue).sum();
        int targetPerStore = totalForecast == 0 ? 0 : Math.max(0, totalInventory - totalForecast); // simplistic

        // Identify donors and receivers
        List<Map.Entry<Long, Integer>> donors = new ArrayList<>();
        List<Map.Entry<Long, Integer>> receivers = new ArrayList<>();
        for (Long storeId : inventoryMap.keySet()) {
            int invQty = inventoryMap.get(storeId);
            int demand = forecastMap.getOrDefault(storeId, 0);
            int diff = invQty - demand;
            if (diff > 0) donors.add(Map.entry(storeId, diff));
            else if (diff < 0) receivers.add(Map.entry(storeId, -diff));
        }

        // Create suggestions by matching donors to receivers
        for (Map.Entry<Long, Integer> donor : donors) {
            int available = donor.getValue();
            for (Iterator<Map.Entry<Long, Integer>> it = receivers.iterator(); it.hasNext() && available > 0; ) {
                Map.Entry<Long, Integer> recv = it.next();
                int need = recv.getValue();
                int qty = Math.min(available, need);
                if (qty <= 0) continue;
                TransferSuggestion ts = new TransferSuggestion();
                ts.setProduct(product);
                ts.setSourceStore(storeRepo.findById(donor.getKey()).orElseThrow());
                ts.setTargetStore(storeRepo.findById(recv.getKey()).orElseThrow());
                ts.setSuggestedQuantity(qty);
                ts.setPriority("MEDIUM");
                ts.setReason("Auto-balanced");
                transferRepo.save(ts);
                suggestions.add(ts);

                available -= qty;
                int remainingNeed = need - qty;
                if (remainingNeed <= 0) it.remove();
                else {
                    // replace with updated need
                    int idx = receivers.indexOf(recv);
                    receivers.set(idx, Map.entry(recv.getKey(), remainingNeed));
                }
            }
        }

        return suggestions;
    }

    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return transferRepo.findBySourceStoreId(storeId);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
