package com.example.demo.repository;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Store;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    Optional<InventoryLevel> findByStoreAndProduct(Store store, Product product);
}
