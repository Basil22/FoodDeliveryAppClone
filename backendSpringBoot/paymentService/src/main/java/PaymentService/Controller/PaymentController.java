package PaymentService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PaymentService.Entity.Payment;
import PaymentService.Entity.PaymentStatus;
import PaymentService.Entity.PaymentType;
import PaymentService.Exception.PaymentException;
import PaymentService.Repository.PaymentRepository;
import PaymentService.Service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PaymentRepository paymentRepository;

	@PostMapping("/process")
	public ResponseEntity<String> processPayment(@RequestParam Long cartId, @RequestParam PaymentType paymentType) {
		try {
			String response = paymentService.processPayment(cartId, paymentType);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (PaymentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long paymentId) {
		try {
			Payment payment = paymentService.getPaymentDetails(paymentId);
			return new ResponseEntity<>(payment, HttpStatus.OK);
		} catch (PaymentException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{paymentId}/status")
	public ResponseEntity<String> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status,
			@RequestParam PaymentType paymentType) {
		try {
			String updatedPayment = paymentService.updatePaymentStatus(paymentId, status, paymentType);
			return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
		} catch (PaymentException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/complete-payment")
	public ResponseEntity<String> completePayment(@RequestParam Long paymentId, @RequestParam String paymentStatus) {
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new PaymentException("Payment not found"));

		if ("SUCCESS".equalsIgnoreCase(paymentStatus)) {
			payment.setStatus(PaymentStatus.SUCCESS);
		} else {
			payment.setStatus(PaymentStatus.FAILED);
		}

		paymentRepository.save(payment);

		return new ResponseEntity<>("Payment " + payment.getStatus(), HttpStatus.OK);
	}

}