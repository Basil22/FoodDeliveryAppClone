package com.zomato.cartService.DTO;

import java.util.List;

public class VendorDTO {
	private int vendorId;

	private String vendorName;
	private String vendorContactNumber;
	private String vendorAddress;
	private boolean isOpen;

	
	

	private List<ItemsDTO> itemList;

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorContactNumber() {
		return vendorContactNumber;
	}

	public void setVendorContactNumber(String vendorContactNumber) {
		this.vendorContactNumber = vendorContactNumber;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<ItemsDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemsDTO> itemList) {
		this.itemList = itemList;
	}
	

}
