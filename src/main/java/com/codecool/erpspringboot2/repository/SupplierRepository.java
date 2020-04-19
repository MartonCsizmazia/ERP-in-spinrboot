package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
