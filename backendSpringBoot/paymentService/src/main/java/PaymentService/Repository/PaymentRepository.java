package PaymentService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import PaymentService.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
