package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}