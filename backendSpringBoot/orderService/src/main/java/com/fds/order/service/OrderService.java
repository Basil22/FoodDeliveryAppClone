package com.fds.order.service;

import java.util.List;

import com.fds.order.dto.OrderDto;
import com.fds.order.entity.Order;
import com.fds.order.exception.IdNotFoundException;
import com.fds.order.exception.OrderStatusNotFoundException;

public interface OrderService {

	public OrderDto placeOrder(Order order) throws IdNotFoundException, OrderStatusNotFoundException;

	public Order updateOrder(Order order) throws IdNotFoundException, OrderStatusNotFoundException;

	public String deleteOrderById(Long orderId) throws IdNotFoundException;

	public Order getOrderById(Long orderId) throws IdNotFoundException;

	public List<Order> getAllOrders();

	public List<Order> getOrderByUserId(Long id);

	public List<Order> getorderByVendorName(String name);

	public List<Order> getorderByVendorNameStatus(String name, String status);
	
	
	
}
