package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class TransferSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store sourceStore;

    @ManyToOne
    private Store targetStore;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private String priority;
    private String status;

    private Instant suggestedAt;

    @PrePersist
    public void prePersist() {
        this.suggestedAt = Instant.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    // getters & setters
    public Long getId() { return id; }
    public Store getSourceStore() { return sourceStore; }
    public void setSourceStore(Store sourceStore) { this.sourceStore = sourceStore; }
    public Store getTargetStore() { return targetStore; }
    public void setTargetStore(Store targetStore) { this.targetStore = targetStore; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public Instant getSuggestedAt() { return suggestedAt; }
}
