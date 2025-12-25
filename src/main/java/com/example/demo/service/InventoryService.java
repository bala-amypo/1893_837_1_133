package com.example.demo.service;
import com.example.demo.entity.InventoryLevel;
import java.util.List;

public interface InventoryService {
    InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity);
    List<InventoryLevel> getInventoryByStore(Long storeId);
}