package com.codecool.erpspringboot2.service;


import com.codecool.erpspringboot2.model.Stock;
import com.codecool.erpspringboot2.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock getStock() {
        return stockRepository.findAll().get(0);
    }
}
