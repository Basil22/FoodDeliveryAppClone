package com.fds.payments.service;

import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fds.payments.dto.CartDTO;
import com.fds.payments.entity.Payment;
import com.fds.payments.entity.PaymentStatus;
import com.fds.payments.entity.PaymentType;
import com.fds.payments.exception.PaymentException;
import com.fds.payments.repository.PaymentRepository;
 
@Service
public class PaymentServiceImp implements PaymentService{
 
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private RestTemplate restTemplate;

    private static final String CART_SERVICE_URL = "http://localhost:8084/api/carts/";    
 
 
    @Override
    public String updatePaymentStatus(Long paymentId, PaymentStatus status, PaymentType paymentType) throws PaymentException {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(status);
            payment.setPaymentType(paymentType);
            paymentRepository.save(payment);
            return "Updated Details";
        } else {
            throw new PaymentException("Payment not found for ID: " + paymentId);
        }
    }
 
    @Override
    public Payment getPaymentDetails(Long paymentId) throws PaymentException {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Payment not found for Payment ID: " + paymentId));
    }
    @Override
    public String processPayment(Long cartId, PaymentType paymentType) throws PaymentException {

        CartDTO cartDTO = getCartDetails(cartId);
 
        if (cartDTO == null || cartDTO.getTotalPrice() == 0.0) {
            throw new PaymentException("Failed to retrieve cart details or amount from Cart Service.");
        }

        Payment payment = new Payment();
        payment.setCartId(cartDTO.getId());
        payment.setAmount(cartDTO.getTotalPrice());
 
        
        if (paymentType == null) {
            throw new PaymentException("Payment type must be provided by the user.");
        }
 
        if (paymentType.equals(PaymentType.CASH_ON_DELIVERY)) {
            payment.setPaymentType(paymentType);
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            return "Cash on Delivery selected. Please confirm your order.";
        } else if (paymentType.equals(PaymentType.ONLINE)) {
            payment.setPaymentType(paymentType);
            payment.setStatus(PaymentStatus.PENDING);  
            paymentRepository.save(payment);
 
            
            return "Redirecting to transaction page for online payment. Select payment method.";
        } else {
            throw new PaymentException("Unsupported payment type.");
        }
    }
 
 
    private CartDTO getCartDetails(Long cartId) {
        try {
            String url = CART_SERVICE_URL + cartId;
            return restTemplate.getForObject(url, CartDTO.class);
        } catch (Exception e) {
            throw new PaymentException("Failed to retrieve cart details from Cart Service: " + e.getMessage());
        }
    }
 
}