package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
