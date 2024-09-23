package com.cg.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.order.entity.Order;

public interface OrderDao extends JpaRepository<Order, Long> {
	List<Order> findByUserId(Long userId);

	List<Order> findByVendorName(String name);

	List<Order> findByVendorNameAndStatus(String name, String status);

	



}
