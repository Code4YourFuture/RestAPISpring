package com.example.demo.repository;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findByNameContaining(String name);
    public List<Product> findByPriceBetween(BigDecimal lowerRange, BigDecimal upperRange);
}
