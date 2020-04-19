package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Lineitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineitemRepository extends JpaRepository<Lineitem, Long> {
    List<Lineitem> getAllByFakeDeliveryKey(Long id);
}
