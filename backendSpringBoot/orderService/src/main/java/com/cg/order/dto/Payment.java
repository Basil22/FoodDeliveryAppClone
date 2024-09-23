package com.cg.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	private Long paymentId;
    private Long cartId;
    private Double amount;
    private PaymentStatus status;
    private PaymentType paymentType;

}
