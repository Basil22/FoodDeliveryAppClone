package PaymentService.Service;

import PaymentService.Entity.Payment;
import PaymentService.Entity.PaymentStatus;
import PaymentService.Entity.PaymentType;
import PaymentService.Exception.PaymentException;

public interface PaymentService {

	public String processPayment(Long cartId, PaymentType paymentType) throws PaymentException;

	public String updatePaymentStatus(Long paymentId, PaymentStatus status, PaymentType paymentStatus)
			throws PaymentException;

	public Payment getPaymentDetails(Long paymentId) throws PaymentException;

}
