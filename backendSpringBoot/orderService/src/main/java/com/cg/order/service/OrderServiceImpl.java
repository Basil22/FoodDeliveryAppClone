package com.cg.order.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.order.dao.OrderDao;
import com.cg.order.dto.CartDTo;
import com.cg.order.dto.OrderDto;
import com.cg.order.dto.Payment;
import com.cg.order.dto.PaymentStatus;
import com.cg.order.dto.User;
import com.cg.order.dto.Vendor;
import com.cg.order.entity.Order;
import com.cg.order.exception.IdNotFoundException;
import com.cg.order.exception.OrderStatusNotFoundException;
import com.cg.order.feignClient.CartClient;
import com.cg.order.feignClient.PaymentClient;
import com.cg.order.feignClient.UserClient;
import com.cg.order.feignClient.VendorClient;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private UserClient userClient;

	@Autowired
	private PaymentClient paymentClient;

	@Autowired
	private VendorClient vendorClient;

	@Autowired
	private CartClient cartClient;

	@Override
	public OrderDto placeOrder(Order orders) throws IdNotFoundException, OrderStatusNotFoundException {

		Order order = new Order();
		OrderDto order1 = new OrderDto();

		ResponseEntity<User> userById = userClient.getUserById(orders.getUserId());

		order1.setUserId(userById.getBody().getUserId());
		order.setUserId(userById.getBody().getUserId());
		
		ResponseEntity<Payment> paymentDetails = paymentClient.getPaymentDetails(orders.getPaymentId());
		order1.setPaymentId(paymentDetails.getBody().getPaymentId());

		order.setPaymentId(order1.getPaymentId());
		if (PaymentStatus.PENDING.equals(paymentDetails.getBody().getStatus())) {
			order.setPaymentId(order1.getPaymentId());
		} else {
			throw new IdNotFoundException("Payment status should be pending");
		}
		ResponseEntity<Vendor> viewVendorDetailsByName = vendorClient.viewVendorDetailsByName(orders.getVendorName());
		order1.setVendorName(viewVendorDetailsByName.getBody().getVendorName());
		order.setVendorName(order1.getVendorName());

		order.setTotalPrice(paymentDetails.getBody().getAmount());// totalprice
		order1.setTotalPrice(paymentDetails.getBody().getAmount());
		if (orders.getStatus().equalsIgnoreCase("placed")) {
			order.setStatus("PLACED");
			order1.setStatus("PLACED");
		} else {
			throw new OrderStatusNotFoundException("Status should be placed");
		}
		order.setTimeStamp(LocalDate.now());
		paymentClient.updatePaymentStatus(order1.getPaymentId(), PaymentStatus.SUCCESS,
				paymentDetails.getBody().getPaymentType());
		ResponseEntity<CartDTo> cartById = cartClient.viewCartById(orders.getCartId());
		
		order.setCartId(orders.getCartId());
		Order o = orderDao.save(order);
		System.out.println(o);
		CartDTo cart = new CartDTo();
		cart.setId(cartById.getBody().getId());
		cart.setItemQuantities(cartById.getBody().getItemQuantities());
		System.out.println(cartById.getBody());
		
		order1.setCartDTO(cart);
		
		order1.setOrderId(o.getOrderId());
		return order1;
		
	}

	@Override
	public Order updateOrder(Order order) throws IdNotFoundException, OrderStatusNotFoundException {
		Order existingOrder = orderDao.findById(order.getOrderId())
				.orElseThrow(() -> new IdNotFoundException("Order ID not found to update the order"));

		ResponseEntity<Payment> paymentDetails = paymentClient.getPaymentDetails(order.getPaymentId());

		// order.setPaymentId(order1.getPaymentDto().getPaymentId());
		if (order.getStatus().equalsIgnoreCase("cancelled")) {
			existingOrder.setStatus("CANCELLED");
		} else {
			throw new OrderStatusNotFoundException("Status should be cancelled");
		}
		existingOrder.setTimeStamp(LocalDate.now());
		paymentClient.updatePaymentStatus(existingOrder.getPaymentId(), PaymentStatus.FAILED,
				paymentDetails.getBody().getPaymentType());
		return orderDao.save(existingOrder);
	}

	@Override
	public String deleteOrderById(Long orderId) throws IdNotFoundException {
		Optional<Order> existingOrder = orderDao.findById(orderId);
		if (existingOrder.isPresent()) {
			orderDao.deleteById(orderId);
		} else {
			throw new IdNotFoundException("Order ID not found to delete the order");
		}
		return orderId + " is deleted";
	}

	@Override
	public Order getOrderById(Long orderId) throws IdNotFoundException {
		return orderDao.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order ID " + orderId + " not found"));
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderDao.findAll();
		if (orders.isEmpty()) {
			throw new IdNotFoundException("No orders found in the system.");
		}
		return orders;
	}

	@Override
	public List<Order> getOrderByUserId(Long id) {
		List<Order> orders = orderDao.findByUserId(id);
		if (orders.isEmpty()) {
			throw new IdNotFoundException("No orders found By this user id " + id);
		}
		return orders;
	}

	@Override
	public List<Order> getorderByVendorName(String name) {
		List<Order> orders = orderDao.findByVendorName(name);
		if (orders.isEmpty()) {
			throw new IdNotFoundException("No orders found By this restaurant name " + name);
		}
		return orders;
	}

	@Override
	public List<Order> getorderByVendorNameStatus(String name, String status) {
		List<Order> orders = orderDao.findByVendorNameAndStatus(name, status);
		if (orders.isEmpty()) {
			throw new IdNotFoundException(
					"No orders found By this restaurant name " + name + " with this status " + status);
		}
		return orders;
	}

}
