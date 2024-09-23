package com.cg.order.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "orderSer")
@ToString

public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "cart_id")
	private Long cartId;

	@Column(name = "payment_id")
	private Long paymentId;

	@Column(name = "vendor_name")
	private String vendorName;

	@Column(name = "total_price")
	private Double totalPrice;

	@Column(name = "status")
	private String status;

	@Column(name = "time_stamp")
	private LocalDate timeStamp;
	
	public Order() {
		
	}

	public Order(Long orderId, Long userId, Long cartId, Long paymentId, String vendorName, Double totalPrice,
			String status, LocalDate timeStamp) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.cartId = cartId;
		this.paymentId = paymentId;
		this.vendorName = vendorName;
		this.totalPrice = totalPrice;
		this.status = status;
		this.timeStamp = timeStamp;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

}
