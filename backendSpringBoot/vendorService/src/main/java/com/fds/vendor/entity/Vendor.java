package com.fds.vendor.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendorId;

	@NotBlank(message = "Vendor name cannot be null or blank.")
	@Pattern(regexp = "^(.{4,})$", message = "Vendor name must have atleast 4 characters.")
	private String vendorName;

	@NotBlank(message = "Contact number cannot be null or blank.")
	@Pattern(regexp = "^(?!([6-9])\\1{9})[6-9]\\d{9}$", message = "Phone number must be 10 digits and start with 6, 7, 8, or 9")
	private String vendorContactNumber;

	@NotBlank(message = "Address cannot be null or blank.")
	@Pattern(regexp = "^(.{10,})$", message = "Address must have atleast 10 characters.")
	private String vendorAddress;

	@NotBlank(message = "Fssai license cannot be null or blank.")
	@Pattern(regexp = "^\\d{14}$", message = "Fssai must be 14 digits.")
	private String fssaiLicenseNumber;

	private boolean isOpen;

	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Items> itemList;

	// BOILERPLATE CODE BELOW

	public Vendor() {

	}

	public Vendor(int vendorId, String vendorName, String vendorContactNumber, String vendorAddress, String fssai,
			boolean isOpen, List<Items> itemList) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.vendorContactNumber = vendorContactNumber;
		this.vendorAddress = vendorAddress;
		this.fssaiLicenseNumber = fssai;
		this.isOpen = isOpen;
		this.itemList = itemList;
	}

	public String getFssaiLicenseNumber() {
		return fssaiLicenseNumber;
	}

	public void setFssaiLicenseNumber(String fssaiLicenseNumber) {
		this.fssaiLicenseNumber = fssaiLicenseNumber;
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

	public String getContactNumber() {
		return vendorContactNumber;
	}

	public void setContactNumber(String vendorContactNumber) {
		this.vendorContactNumber = vendorContactNumber;
	}

	public String getAddress() {
		return vendorAddress;
	}

	public void setAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<Items> getItemList() {
		return itemList;
	}

	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}
}