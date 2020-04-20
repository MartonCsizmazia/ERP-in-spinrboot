package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Lineitem;
import com.codecool.erpspringboot2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
