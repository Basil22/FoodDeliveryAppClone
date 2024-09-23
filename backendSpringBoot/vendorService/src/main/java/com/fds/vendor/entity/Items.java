package com.fds.vendor.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;

	@NotBlank(message = "Item name cannot be null or blank.")
	@Pattern(regexp = "^[A-Za-z][A-Za-z ]{3,}$", message = "Item name must have a minimum length of 4 letters, may contain spaces, and must not include numbers or special characters.")
	private String itemName;

	@NotBlank(message = "Item category cannot be null or blank.")
	@Pattern(regexp = "^(breakfast|lunch|dinner|sides|drinks)$", message = "Category can only be breakfast, lunch, dinner, sides or drinks.")
	private String itemCategory;

	@PositiveOrZero(message = "Item price cannot be negative.")
	private double itemPrice;

	@NotBlank(message = "Item description cannot be null or blank.")
	@Pattern(regexp = "^(.{10,})$", message = "Item description must have atleast 10 characters.")
	private String itemDescription;

	@PositiveOrZero(message = "Item ratings cannot be negative")
	@DecimalMin(value = "0.0", inclusive = true, message = "Item ratings must be atleast 0.0")
	@DecimalMax(value = "5.0", inclusive = true, message = "Item ratings cannot be more than 5.0")
	private double itemRatings;

	private boolean isAvailable;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "vendorId")
	@JsonBackReference
	private Vendor vendor;

	// BOILERPLATE CODE BELOW

	public Items() {

	}

	public Items(int itemId, String itemName, String itemCategory, double itemPrice, String itemDescription,
			double itemRatings, boolean isAvailable, Vendor vendor) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemPrice = itemPrice;
		this.itemDescription = itemDescription;
		this.itemRatings = itemRatings;
		this.isAvailable = isAvailable;
		this.vendor = vendor;
	}

	public double getRatings() {
		return itemRatings;
	}

	public void setRatings(double itemRatings) {
		this.itemRatings = itemRatings;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategory() {
		return itemCategory;
	}

	public void setCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getDescription() {
		return itemDescription;
	}

	public void setDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public double getPrice() {
		return itemPrice;
	}

	public void setPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
}