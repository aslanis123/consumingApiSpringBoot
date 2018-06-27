package com.dimos.tesco.integration.repository;

import com.dimos.tesco.integration.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
