package com.example.demo.repository;
import com.example.demo.entity.InventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    List<InventoryLevel> findByStore_Id(Long storeId);
    Optional<InventoryLevel> findByStore_IdAndProduct_Id(Long storeId, Long productId);
}