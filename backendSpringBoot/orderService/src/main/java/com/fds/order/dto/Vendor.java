package com.fds.order.dto;

import java.util.List;

public class Vendor {
	
	private int vendorId;
	private String vendorName;
	private String vendorContactNumber;
	private String vendorAddress;
	private boolean isOpen;
	private List<Items> itemList;
	
	public Vendor() {
		
	}

	public Vendor(int vendorId, String vendorName, String vendorContactNumber, String vendorAddress, boolean isOpen,
			List<Items> itemList) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.vendorContactNumber = vendorContactNumber;
		this.vendorAddress = vendorAddress;
		this.isOpen = isOpen;
		this.itemList = itemList;
	}

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

	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}

}
