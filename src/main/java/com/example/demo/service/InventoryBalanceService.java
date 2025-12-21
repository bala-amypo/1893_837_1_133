package com.example.demo.service;
import com.example.demo.repository.InventoryRepository;

import com.example.demo.model.InventoryItem;
import com.example.demo.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryBalanceService {

    private final InventoryRepository repo;

    public InventoryBalanceService(InventoryRepository repo) {
        this.repo = repo;
    }

    public List<String> recommendTransfers() {
        List<InventoryItem> items = repo.findAll();

        Map<String, List<InventoryItem>> groupedByProduct = new HashMap<>();

        // Group items by product
        for (InventoryItem item : items) {
            groupedByProduct
                    .computeIfAbsent(item.getProduct(), k -> new ArrayList<>())
                    .add(item);
        }

        List<String> recommendations = new ArrayList<>();

        // For each product, balance locations
        for (String product : groupedByProduct.keySet()) {

            List<InventoryItem> productStock = groupedByProduct.get(product);

            int total = productStock.stream().mapToInt(InventoryItem::getQuantity).sum();
            int avg = total / productStock.size();

            List<InventoryItem> surplus = new ArrayList<>();
            List<InventoryItem> deficit = new ArrayList<>();

            for (InventoryItem item : productStock) {
                if (item.getQuantity() > avg) {
                    surplus.add(item);
                } else if (item.getQuantity() < avg) {
                    deficit.add(item);
                }
            }

            for (InventoryItem s : surplus) {
                for (InventoryItem d : deficit) {

                    int transferable = Math.min(
                            s.getQuantity() - avg,
                            avg - d.getQuantity()
                    );

                    if (transferable > 0) {
                        recommendations.add(
                                "Move " + transferable + " units of '" + product +
                                        "' from " + s.getLocation() +
                                        " → " + d.getLocation()
                        );

                        s.setQuantity(s.getQuantity() - transferable);
                        d.setQuantity(d.getQuantity() + transferable);
                    }
                }
            }
        }
        return recommendations;
    }
}
