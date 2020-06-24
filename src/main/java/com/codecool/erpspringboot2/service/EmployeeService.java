package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.Employee;

import com.codecool.erpspringboot2.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
}
