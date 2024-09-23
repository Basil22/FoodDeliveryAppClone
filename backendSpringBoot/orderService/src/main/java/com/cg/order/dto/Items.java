package com.cg.order.dto;

public class Items {
	private int itemId;
	private String itemName;
	private String itemCategory;
	private double itemPrice;
	private String itemDescription;
	private double itemRatings;
	private boolean isAvailable;
	private Vendor vendor;
	
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

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public double getItemRatings() {
		return itemRatings;
	}

	public void setItemRatings(double itemRatings) {
		this.itemRatings = itemRatings;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}
