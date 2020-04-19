package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
