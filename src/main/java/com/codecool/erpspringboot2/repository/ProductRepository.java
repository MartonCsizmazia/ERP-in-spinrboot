package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
