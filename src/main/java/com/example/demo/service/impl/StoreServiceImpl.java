package com.example.demo.service.impl;

import com.example.demo.entity.Store;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository repo;

    public StoreServiceImpl(StoreRepository repo) { this.repo = repo; }

    @Override
    public Store createStore(Store store) { return repo.save(store); }

    @Override
    public Store getStoreById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    @Override
    public List<Store> getAllStores() { return repo.findAll(); }

    @Override
    public Store updateStore(Long id, Store store) {
        Store s = getStoreById(id);
        s.setName(store.getName());
        s.setLocation(store.getLocation());
        s.setCapacity(store.getCapacity());
        return repo.save(s);
    }
}