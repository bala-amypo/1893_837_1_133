package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transfer_suggestion")
public class TransferSuggestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Store sourceStore;

    @ManyToOne(optional = false)
    private Store targetStore;

    @ManyToOne(optional = false)
    private Product product;

    private Integer suggestedQuantity;
    private String priority = "MEDIUM";
    private Instant generatedAt;
    private String status = "PENDING";
    private String reason;

    @PrePersist
    public void prePersist() {
        if (this.generatedAt == null) this.generatedAt = Instant.now();
        if (this.status == null) this.status = "PENDING";
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Store getSourceStore() { return sourceStore; }
    public void setSourceStore(Store sourceStore) { this.sourceStore = sourceStore; }
    public Store getTargetStore() { return targetStore; }
    public void setTargetStore(Store targetStore) { this.targetStore = targetStore; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getSuggestedQuantity() { return suggestedQuantity; }
    public void setSuggestedQuantity(Integer suggestedQuantity) { this.suggestedQuantity = suggestedQuantity; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public Instant getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(Instant generatedAt) { this.generatedAt = generatedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
