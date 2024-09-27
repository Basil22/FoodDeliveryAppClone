package com.fds.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fds.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);

	List<Order> findByVendorName(String name);

	List<Order> findByVendorNameAndStatus(String name, String status);

}
