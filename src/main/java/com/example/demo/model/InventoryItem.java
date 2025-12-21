package com.example.demo.model;

public class InventoryItem {
    private String location;
    private String product;
    private int quantity;

    public InventoryItem() {}

    public InventoryItem(String location, String product, int quantity) {
        this.location = location;
        this.product = product;
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}
