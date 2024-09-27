package com.fds.cart.entity;

import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;

import java.util.Map;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
   
    @ElementCollection
    @MapKeyColumn(name = "item_name") // The key of the map will be item names
    @Column(name = "quantity")        // The value of the map will be quantities
    private Map<String, Integer> itemQuantities;

    private double totalPrice;
   
    private double gstIncludedPrice;  // Field for GST included price
    private double deliveryCharges;    // Field for delivery charges
    private String vendorName;
    public double getPayableAmount() {
		return PayableAmount;
	}

	public void setPayableAmount(double payableAmount) {
		PayableAmount = payableAmount;
	}

	private double PayableAmount;

    @Enumerated(EnumType.STRING)
    private CouponType coupon;  // Coupon field

    private Double discountedPrice;  // Discounted price field

    private boolean hasCutlery = false; // Initialize with default value

    // Constructors
    public Cart() {}

   

    public Cart(Long id, Long userId, Map<String, Integer> itemQuantities, double totalPrice, double gstIncludedPrice,
			double deliveryCharges, String vendorName, double payableAmount, CouponType coupon, Double discountedPrice,
			boolean hasCutlery) {
		super();
		this.id = id;
		this.userId = userId;
		this.itemQuantities = itemQuantities;
		this.totalPrice = totalPrice;
		this.gstIncludedPrice = gstIncludedPrice;
		this.deliveryCharges = deliveryCharges;
		this.vendorName = vendorName;
		PayableAmount = payableAmount;
		this.coupon = coupon;
		this.discountedPrice = discountedPrice;
		this.hasCutlery = hasCutlery;
	}

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

    public double getGstIncludedPrice() {
        return gstIncludedPrice;
    }

    public void setGstIncludedPrice(double gstIncludedPrice) {
        this.gstIncludedPrice = gstIncludedPrice;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
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


    

    public void setDeliveryChargesAndPayableAmount(int distance) {
        this.deliveryCharges = calculateDeliveryCharges(distance);
        this.PayableAmount = calculatePayableAmount(distance);
    }
    public double calculateGstCharge() {
        return this.totalPrice * (0.18 + 0.18);  // GST as a sum of CGST + SGST
    }


    public int calculateDeliveryCharges(double distance) {
    	if(distance <=3) {
    		return 15;
    	}
        return 30; // 5 Rs per km
    }

    public double calculatePayableAmount(double distance) {
        double gstCharge = calculateGstCharge();
        double deliveryCharges = calculateDeliveryCharges(distance);
        return this.totalPrice + gstCharge + deliveryCharges;
    }

    public void updateCharges(double distance) {
    	 this.gstIncludedPrice = calculateGstCharge();  // Set GST price
         this.deliveryCharges = calculateDeliveryCharges(distance);  // Set delivery charges
         this.PayableAmount = calculatePayableAmount(distance); 
    }

}

