package com.fds.vendor.service;

import java.util.List;

import com.fds.vendor.entity.Items;
import com.fds.vendor.entity.Vendor;

public interface ByIdService {

	public Vendor viewVendorById(int vendorId);
	
	public Items viewItemByInVendorById(int vendorId, int itemId);
	public List<Items> viewAllItemsInVendorById(int vendorId);
	
	public List<Items> getItemsByCategory(int vendorId, String categoryName);
}
