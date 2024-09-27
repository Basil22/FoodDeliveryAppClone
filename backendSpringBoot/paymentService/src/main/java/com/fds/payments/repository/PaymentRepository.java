package com.fds.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fds.payments.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
