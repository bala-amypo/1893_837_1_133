package com.example.demo.service.impl;  

import com.example.demo.entity.Product;  
import com.example.demo.exception.BadRequestException;  
import com.example.demo.exception.ResourceNotFoundException;  
import com.example.demo.repository.ProductRepository;  
import com.example.demo.service.ProductService;  
import org.springframework.stereotype.Service;  

import java.util.List;  

@Service  
public class ProductServiceImpl implements ProductService {  
    
    private final ProductRepository productRepository;  
    
    public ProductServiceImpl(ProductRepository productRepository) {  
        this.productRepository = productRepository;  
    }  
    
    @Override  
    public Product createProduct(Product product) {  
        Product existing = productRepository.findBySku(product.getSku());  
        if (existing != null) {  
            throw new BadRequestException("SKU already exists");  
        }  
        return productRepository.save(product);  
    }  
    
    @Override  
    public Product getProductById(Long id) {  
        return productRepository.findById(id)  
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));  
    }  
    
    @Override  
    public List<Product> getAllProducts() {  
        return productRepository.findAll();  
    