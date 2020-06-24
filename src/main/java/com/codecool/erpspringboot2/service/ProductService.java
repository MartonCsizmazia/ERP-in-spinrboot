package com.codecool.erpspringboot2.service;


import com.codecool.erpspringboot2.model.Product;
import com.codecool.erpspringboot2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
}
