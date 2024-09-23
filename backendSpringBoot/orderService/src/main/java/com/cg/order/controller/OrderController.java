package com.cg.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.order.dto.OrderDto;
import com.cg.order.entity.Order;
import com.cg.order.exception.IdNotFoundException;
import com.cg.order.exception.OrderStatusNotFoundException;
import com.cg.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/placeOrder")
	public ResponseEntity<OrderDto> placeOrder(@RequestBody Order order)
			throws IdNotFoundException, OrderStatusNotFoundException {
		OrderDto ord = orderService.placeOrder(order);
		return new ResponseEntity<>(ord, HttpStatus.CREATED);
	}

	@PutMapping("/updateOrderStatus/{id}")
	public ResponseEntity<Order> updateOrderById(@PathVariable Long id, @RequestBody Order order)
			throws IdNotFoundException, OrderStatusNotFoundException {
		order.setOrderId(id);
		return ResponseEntity.ok(orderService.updateOrder(order));
	}

	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<String> deleteOrderById(@PathVariable Long id) throws IdNotFoundException {
		return ResponseEntity.ok(orderService.deleteOrderById(id));
	}

	@GetMapping("/orderByorderId/{id}")
	public ResponseEntity<Order> getorderById(@PathVariable Long id) throws IdNotFoundException {
		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@GetMapping("/allOrders")
	public ResponseEntity<List<Order>> getAllOrders() throws IdNotFoundException {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@GetMapping("/orderByUserId/{id}")
	public ResponseEntity<List<Order>> getorderByUserId(@PathVariable Long id) throws IdNotFoundException {
		return ResponseEntity.ok(orderService.getOrderByUserId(id));
	}

	@GetMapping("/orderByVendorName/{name}")
	public ResponseEntity<List<Order>> getorderByVendorName(@PathVariable String name) throws IdNotFoundException {
		return ResponseEntity.ok(orderService.getorderByVendorName(name));
	}

	@GetMapping("/orderByVendorNameStatus/{name}/{status}")
	public ResponseEntity<List<Order>> getorderByVendorName(@PathVariable String name, @PathVariable String status)
			throws IdNotFoundException {
		String statuss = status.toUpperCase();
		if (!(statuss.equalsIgnoreCase("PLACED") || statuss.equalsIgnoreCase("CANCELLED"))) {
			throw new IdNotFoundException("Status should be PLACED or CANCELLLED");
		}
		return ResponseEntity.ok(orderService.getorderByVendorNameStatus(name, statuss));
	}

}
