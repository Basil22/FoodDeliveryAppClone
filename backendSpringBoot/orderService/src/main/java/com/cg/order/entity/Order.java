package com.cg.order.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orderSer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
}
