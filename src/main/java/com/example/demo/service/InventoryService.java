package com.example.demo.service;

import com.example.demo.model.InventoryItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    private final List<InventoryItem> inventory = new ArrayList<>();

    public List<InventoryItem> getAll() {
        return inventory;
    }

    public InventoryItem addItem(InventoryItem item) {
        inventory.add(item);
        return item;
    }
}
