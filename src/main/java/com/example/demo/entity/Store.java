package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "storeName")
})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String storeName;

    private String address;
    private String region;

    private Boolean active = true;

    // getters & setters
    public Long getId() { return id; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
