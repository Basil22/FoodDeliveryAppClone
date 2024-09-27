package com.fds.order.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fds.order.dto.CartDTO;
import com.fds.order.dto.OrderDto;
import com.fds.order.dto.Payment;
import com.fds.order.dto.PaymentStatus;
import com.fds.order.dto.User;
import com.fds.order.dto.Vendor;
import com.fds.order.entity.Order;
import com.fds.order.exception.IdNotFoundException;
import com.fds.order.exception.OrderStatusNotFoundException;
import com.fds.order.feignClient.CartClient;
import com.fds.order.feignClient.PaymentClient;
import com.fds.order.feignClient.UserClient;
import com.fds.order.feignClient.VendorClient;
import com.fds.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepo;

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
		ResponseEntity<CartDTO> cartById = cartClient.viewCartById(orders.getCartId());
		
		order.setCartId(orders.getCartId());
		Order o = orderRepo.save(order);
		System.out.println(o);
		CartDTO cart = new CartDTO();
		cart.setId(cartById.getBody().getId());
		cart.setItemQuantities(cartById.getBody().getItemQuantities());
		System.out.println(cartById.getBody());
		
		order1.setCartDTO(cart);
		
		order1.setOrderId(o.getOrderId());
		return order1;
		
	}

	@Override
	public Order updateOrder(Order order) throws IdNotFoundException, OrderStatusNotFoundException {
		Order existingOrder = orderRepo.findById(order.getOrderId())
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
		return orderRepo.save(existingOrder);
	}

	@Override
	public String deleteOrderById(Long orderId) throws IdNotFoundException {
		Optional<Order> existingOrder = orderRepo.findById(orderId);
		if (existingOrder.isPresent()) {
			orderRepo.deleteById(orderId);
		} else {
			throw new IdNotFoundException("Order ID not found to delete the order");
		}
		return orderId + " is deleted";
	}

	@Override
	public Order getOrderById(Long orderId) throws IdNotFoundException {
		return orderRepo.findById(orderId)
				.orElseThrow(() -> new IdNotFoundException("Order ID " + orderId + " not found"));
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderRepo.findAll();
		if (orders.isEmpty()) {
			throw new IdNotFoundException("No orders found in the system.");
		}
		return orders;
	}

	@Override
	public List<Order> getOrderByUserId(Long id) {
		List<Order> orders = orderRepo.findByUserId(id);
		if (orders.isEmpty()) {
			throw new IdNotFoundException("No orders found By this user id " + id);
		}
		return orders;
	}

	@Override
	public List<Order> getorderByVendorName(String name) {
		List<Order> orders = orderRepo.findByVendorName(name);
		if (orders.isEmpty()) {
			throw new IdNotFoundException("No orders found By this restaurant name " + name);
		}
		return orders;
	}

	@Override
	public List<Order> getorderByVendorNameStatus(String name, String status) {
		List<Order> orders = orderRepo.findByVendorNameAndStatus(name, status);
		if (orders.isEmpty()) {
			throw new IdNotFoundException(
					"No orders found By this restaurant name " + name + " with this status " + status);
		}
		return orders;
	}

}
