package com.example.demo.repository;
import com.example.demo.entity.InventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    Optional<InventoryLevel> findByStore_IdAndProduct_Id(Long storeId, Long productId);
    List<InventoryLevel> findByStore_Id(Long storeId);
    List<InventoryLevel> findByProduct_Id(Long productId);
}
