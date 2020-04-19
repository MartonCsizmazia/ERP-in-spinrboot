package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Lineitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineitemRepository extends JpaRepository<Lineitem, Long> {
}
