package com.cg.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

	CartDTo CartDTO;
	long orderId;
	long userId;
	long paymentId;
	String vendorName;
	String status;
	double totalPrice;
	
}
//{
//    "orderId": 1,
//    "userId": 1,
//    "cartId": null,
//    "paymentId": 3,
//    "vendorName": "sri udupi park",
//    "totalPrice": 220.0,
//    "status": "PLACED",
//    "timeStamp": "2024-09-22"
