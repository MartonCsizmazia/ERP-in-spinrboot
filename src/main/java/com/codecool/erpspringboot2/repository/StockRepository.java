package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}