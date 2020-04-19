package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.Inventory;
import com.codecool.erpspringboot2.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory getInvetory(){
        return inventoryRepository.findAll().get(0);
    }
}