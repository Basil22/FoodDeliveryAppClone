package com.cg.order.dto;

public class Payment {
	private Long paymentId;
	private Long cartId;
	private Double amount;
	private PaymentStatus status;
	private PaymentType paymentType;
	
	public Payment() {
		
	}

	public Payment(Long paymentId, Long cartId, Double amount, PaymentStatus status, PaymentType paymentType) {
		super();
		this.paymentId = paymentId;
		this.cartId = cartId;
		this.amount = amount;
		this.status = status;
		this.paymentType = paymentType;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

}
