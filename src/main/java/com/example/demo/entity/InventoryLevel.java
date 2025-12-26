package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "inventory_level", uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "product_id"}))
public class InventoryLevel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Store store;

    @ManyToOne(optional = false)
    private Product product;

    private Integer quantity;
    private Instant lastUpdated;

    @PrePersist
    public void prePersist() {
        if (this.lastUpdated == null) this.lastUpdated = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = Instant.now();
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Instant getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Instant lastUpdated) { this.lastUpdated = lastUpdated; }
}
