package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;
    public ProductServiceImpl(ProductRepository repo){ this.repo = repo; }

    @Override
    public Product createProduct(Product product){
        if(product.getSku()==null || product.getSku().isBlank())
            throw new BadRequestException("SKU required");
        return repo.save(product);
    }

    @Override
    public void deactivateProduct(Long id){
        Product p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        p.setActive(false);
        repo.save(p);
    }

    @Override
    public Product getProductById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<Product> findAll(){ return repo.findAll(); }
}
