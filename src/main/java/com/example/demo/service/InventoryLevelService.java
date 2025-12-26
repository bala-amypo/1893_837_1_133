package com.example.demo.service;
import com.example.demo.entity.InventoryLevel;
import java.util.List;

public interface InventoryLevelService {
    InventoryLevel createOrUpdateInventory(InventoryLevel inv);
    List<InventoryLevel> getInventoryForStore(Long storeId);
    List<InventoryLevel> getInventoryForProduct(Long productId);
    InventoryLevel getSuggestionById(Long id);
    List<InventoryLevel> findAll();
}
