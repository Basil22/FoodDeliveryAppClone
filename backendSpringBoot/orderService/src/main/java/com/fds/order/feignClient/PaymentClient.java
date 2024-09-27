package com.fds.order.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fds.order.dto.Payment;
import com.fds.order.dto.PaymentStatus;
import com.fds.order.dto.PaymentType;

@FeignClient(name = "PaymentService", contextId = "paymentClient")
public interface PaymentClient {
	@GetMapping("/api/payments/{paymentId}")
	public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long paymentId);
	
	@PutMapping("/api/payments/{paymentId}/status")
	public ResponseEntity<String> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status, @RequestParam PaymentType paymentType);
}
