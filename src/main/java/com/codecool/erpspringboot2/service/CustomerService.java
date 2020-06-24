package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.Customer;
import com.codecool.erpspringboot2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}
