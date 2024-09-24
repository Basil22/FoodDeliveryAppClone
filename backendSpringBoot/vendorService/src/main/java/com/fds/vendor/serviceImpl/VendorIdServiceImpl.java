package com.fds.vendor.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fds.vendor.entity.Items;
import com.fds.vendor.entity.Vendor;
import com.fds.vendor.exception.ItemNotFoundException;
import com.fds.vendor.exception.VendorDoesNotExistException;
import com.fds.vendor.repository.VendorRepository;
import com.fds.vendor.service.ByIdService;

@Service
public class VendorIdServiceImpl implements ByIdService {

	@Autowired
	private VendorRepository vendorRepo;

	@Override
	public Vendor viewVendorById(int vendorId) {
		return vendorRepo.findById(vendorId)
				.orElseThrow(() -> new VendorDoesNotExistException("Vendor does not exist."));

	}

	@Override
	public Items viewItemByInVendorById(int vendorId, int itemId) {
		Vendor vendor = vendorRepo.findById(vendorId)
				.orElseThrow(() -> new VendorDoesNotExistException("Vendor does not exist."));

		if (vendor.getItemList() == null || vendor.getItemList().isEmpty()) {
			throw new ItemNotFoundException(vendor.getVendorName() + " does not have any items.");
		}

		Items existingItem = vendor.getItemList().stream().filter(item -> item.getItemId() == itemId).findFirst()
				.orElseThrow(() -> new ItemNotFoundException("Item does not exist in " + vendor.getVendorName()));

		return existingItem;
	}

	@Override
	public List<Items> viewAllItemsInVendorById(int vendorId) {
		Vendor vendor = vendorRepo.findById(vendorId)
				.orElseThrow(() -> new VendorDoesNotExistException("Vendor does not exist."));
		return vendor.getItemList();
	}

	@Override
	public List<Items> getItemsByCategory(int vendorId, String categoryName) {
		Vendor vendor = vendorRepo.findById(vendorId)
				.orElseThrow(() -> new VendorDoesNotExistException("Vendor does not exist."));

		if (vendor.getItemList() == null || vendor.getItemList().isEmpty()) {
			throw new ItemNotFoundException(vendor.getVendorName() + " does not have any items.");
		}

		List<Items> itemsByCategory = vendor.getItemList().stream()
				.filter(item -> item.getCategory().equals(categoryName.toLowerCase())).collect(Collectors.toList());

		if (itemsByCategory.isEmpty()) {
			throw new ItemNotFoundException(
					vendor.getVendorName() + " does not have any items in the category: " + categoryName);
		}

		return itemsByCategory;
	}
}
