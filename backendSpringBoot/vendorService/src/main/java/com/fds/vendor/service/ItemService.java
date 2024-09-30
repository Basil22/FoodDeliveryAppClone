package com.fds.vendor.service;

import java.util.List;
import java.util.Optional;

import com.fds.vendor.entity.Items;
import com.fds.vendor.entity.Vendor;

public interface ItemService {

	// Add item with restaurant name
	public String addItemToVendor(Items item, String vendorName);

	// update item in Vendor
	public String updateItemDetailsInVendor(String vendorName, String itemName, Items item);

	// delete item
	public String deleteItemFromVendor(String vendorName, String itemName);

	// view item
	public Items viewItemInRestaurant(String vendorName, String itemName);

	// toggle item available
	public void itemAvailabilityToggle(String vendorName, String itemName);

	// view all items in vendor
	public List<Items> viewItemsInVendor(String vendorName);

	// Search item and return all vendors with that item
	public List<Vendor> allVendorsWithItem(String itemName);

	// Show all available items
	public List<Items> allAvailableItemsInVendor(String vendorName);
	
	public List<Items> viewAllItems();
	
	public Optional<Items> getItemByName(String itemName);
}
