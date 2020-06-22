package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.Expense;
import com.codecool.erpspringboot2.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }
}
