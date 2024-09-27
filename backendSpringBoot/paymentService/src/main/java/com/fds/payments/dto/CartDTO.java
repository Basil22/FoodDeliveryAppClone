package com.fds.payments.dto;

import java.util.Map;

public class CartDTO {
	 
    private Long id;
    private Long userId;
    private Map<String, Integer> itemQuantities;
    private double totalPrice;
    private String vendorName;
    private CouponType coupon;  
    private Double discountedPrice;
    private boolean hasCutlery;
 
    // Getters and Setters
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public Map<String, Integer> getItemQuantities() {
        return itemQuantities;
    }
 
    public void setItemQuantities(Map<String, Integer> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }
 
    public double getTotalPrice() {
        return totalPrice;
    }
 
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
 
    public String getVendorName() {
        return vendorName;
    }
 
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
 
    public CouponType getCoupon() {
        return coupon;
    }
 
    public void setCoupon(CouponType coupon) {
        this.coupon = coupon;
    }
 
    public Double getDiscountedPrice() {
        return discountedPrice;
    }
 
    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
 
    public boolean isHasCutlery() {
        return hasCutlery;
    }
 
    public void setHasCutlery(boolean hasCutlery) {
        this.hasCutlery = hasCutlery;
    }
}