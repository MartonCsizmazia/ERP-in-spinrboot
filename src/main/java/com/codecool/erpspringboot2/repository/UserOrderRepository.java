package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {
}
