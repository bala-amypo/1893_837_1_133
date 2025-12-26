package com.example.demo.service.impl;
import com.example.demo.entity.Store;
import com.example.demo.exception.*;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository repo;
    public StoreServiceImpl(StoreRepository repo){ this.repo=repo; }

    public Store createStore(Store s){
        if(repo.findByStoreName(s.getStoreName()).isPresent())
            throw new BadRequestException("Store already exists");
        return repo.save(s);
    }

    public Store updateStore(Long id, Store u){
        Store s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        s.setStoreName(u.getStoreName());
        s.setAddress(u.getAddress());
        s.setRegion(u.getRegion());
        s.setActive(u.isActive());
        return repo.save(s);
    }

    public void deactivateStore(Long id){
        Store s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        s.setActive(false);
        repo.save(s);
    }

    public Store getStoreById(Long id){ return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found")); }
    public List<Store> findAll(){ return repo.findAll(); }
}
