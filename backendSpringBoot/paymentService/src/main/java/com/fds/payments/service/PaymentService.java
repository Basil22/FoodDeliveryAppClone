package com.fds.payments.service;

import com.fds.payments.entity.Payment;
import com.fds.payments.entity.PaymentStatus;
import com.fds.payments.entity.PaymentType;
import com.fds.payments.exception.PaymentException;

public interface PaymentService {

	public String processPayment(Long cartId, PaymentType paymentType) throws PaymentException;

	public String updatePaymentStatus(Long paymentId, PaymentStatus status, PaymentType paymentStatus)
			throws PaymentException;

	public Payment getPaymentDetails(Long paymentId) throws PaymentException;

}
