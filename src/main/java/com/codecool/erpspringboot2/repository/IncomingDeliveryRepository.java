package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.IncomingDelivery;
import com.codecool.erpspringboot2.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncomingDeliveryRepository extends JpaRepository<IncomingDelivery, Long> {
    List<IncomingDelivery> findAllByStatusNotLike(Status status);
    List<IncomingDelivery> findAllById(Long id);
}
