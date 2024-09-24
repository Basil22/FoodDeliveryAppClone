package com.fds.vendor.service;

import java.util.List;

import com.fds.vendor.entity.Vendor;

public interface VendorService {

	public String addVendor(Vendor vendor);

	public String updateVendorNameNumberAddressFssaiByName(String vendorName, Vendor vendor);

	public Vendor viewVendorDetailsByName(String vendorName);

	public String deleteVendorByName(String vendorName);

	public void vendorOpenCloseToggle(String vendorName);

	public List<Vendor> viewAllVendors();

	// <7Km Location based searching should be implemented
	public List<Vendor> getNearestVendors(long userId);
	
	public Integer getDistanceBetweenVendorAndUser(long userId, int vendorId);
	
	}
