package com.codecool.erpspringboot2.service;


import com.codecool.erpspringboot2.model.Supplier;
import com.codecool.erpspringboot2.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }
}
