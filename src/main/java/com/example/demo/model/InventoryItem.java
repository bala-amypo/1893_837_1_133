package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;      // For getProduct()
    private int quantity;        // For getQuantity()
    private String location;     // For getLocation()

    // Default constructor
    public InventoryItem() {}

    // Constructor with fields
    public InventoryItem(String product, int quantity, String location) {
        this.product = product;
        this.quantity = quantity;
        this.location = location;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
