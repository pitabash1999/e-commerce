package com.eCcommerce.main.repository;

import com.eCcommerce.main.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Transaction,Long> {
    Optional<Transaction> findByOrderId(Long orderId);
}
