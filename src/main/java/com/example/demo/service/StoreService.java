package com.example.demo.service;
import com.example.demo.entity.Store;
import java.util.List;

public interface StoreService {
    Store createStore(Store store);
    Store getStoreById(Long id);
    List<Store> getAllStores();
    Store updateStore(Long id, Store store);
    
    // Fix: Added missing method
    void deactivateStore(Long id);
}