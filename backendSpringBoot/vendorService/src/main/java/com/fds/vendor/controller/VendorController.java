package com.fds.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fds.vendor.entity.Items;
import com.fds.vendor.entity.Vendor;
import com.fds.vendor.service.ByIdService;
import com.fds.vendor.service.ItemService;
import com.fds.vendor.service.VendorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/vendor")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@Autowired
	private ByIdService idService;

	@Autowired
	private ItemService itemService;

	@PostMapping("/add")
	public ResponseEntity<String> addVendorDetails(@RequestBody @Valid Vendor vendor) {
		return ResponseEntity.status(HttpStatus.CREATED).body(vendorService.addVendor(vendor));
	}

	@PutMapping("/update/{vendorName}") // updates only Name, Number & Address
	public ResponseEntity<String> updateVendorDetailsByName(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName,
			@RequestBody @Valid Vendor vendor) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(vendorService.updateVendorNameNumberAddressFssaiByName(vendorName, vendor));
	}

	@GetMapping("/view/{vendorName}")
	public ResponseEntity<Vendor> viewVendorDetailsByName(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName) {
		return ResponseEntity.status(HttpStatus.OK).body(vendorService.viewVendorDetailsByName(vendorName));
	}

	@DeleteMapping("/delete/{vendorName}")
	public ResponseEntity<String> deleteVendorByName(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName) {
		return ResponseEntity.status(HttpStatus.OK).body(vendorService.deleteVendorByName(vendorName));
	}

	@PatchMapping("/{vendorName}/toggle")
	public void vendorOpenCloseToggle(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName) {
		vendorService.vendorOpenCloseToggle(vendorName);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Vendor>> viewAllVendors() {
		return ResponseEntity.status(HttpStatus.OK).body(vendorService.viewAllVendors());
	}

	@GetMapping("/{vendorId}")
	public ResponseEntity<Vendor> viewVendorById(@PathVariable @Positive int vendorId) {
		return ResponseEntity.status(HttpStatus.OK).body(idService.viewVendorById(vendorId));
	}

	// ITEMS APIS:

	@PostMapping("/{vendorName}/items/add")
	public ResponseEntity<String> addItemToVendor(@RequestBody @Valid Items item,
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName) {
		return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItemToVendor(item, vendorName));
	}

	@PutMapping("/{vendorName}/items/update/{itemName}")
	public ResponseEntity<String> updateItemDetailsInVendor(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName,
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Item name must only contain letters and spaces.") String itemName,
			@RequestBody @Valid Items item) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(itemService.updateItemDetailsInVendor(vendorName, itemName, item));
	}

	@DeleteMapping("/{vendorName}/items/delete/{itemName}")
	public ResponseEntity<String> deleteItemFromVendor(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName,
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Item name must only contain letters and spaces.") String itemName) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.deleteItemFromVendor(vendorName, itemName));
	}

	@GetMapping("/{vendorName}/items/view/{itemName}")
	public ResponseEntity<Items> viewItemInVendor(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName,
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Item name must only contain letters and spaces.") String itemName) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.viewItemInRestaurant(vendorName, itemName));
	}

	@PatchMapping("/{vendorName}/items/{itemName}/toggle")
	public void itemIsAvailableToggle(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName,
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Item name must only contain letters and spaces.") String itemName) {
		itemService.itemAvailabilityToggle(vendorName, itemName);
	}

	@GetMapping("/{vendorName}/items/all")
	public ResponseEntity<List<Items>> viewItemsInVendor(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.viewItemsInVendor(vendorName));
	}

	@GetMapping("/items/{itemName}")
	public ResponseEntity<List<Vendor>> viewAllVendorsWithItem(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Item name must only contain letters and spaces.") String itemName) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.allVendorsWithItem(itemName));
	}

	@GetMapping("/items/available/{vendorName}")
	public ResponseEntity<List<Items>> viewAllAvailableItems(
			@PathVariable @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Vendor name must only contain letters and spaces.") String vendorName) {
		return ResponseEntity.status(HttpStatus.OK).body(itemService.allAvailableItemsInVendor(vendorName));
	}

	@GetMapping("/{vendorId}/items/{itemId}")
	public ResponseEntity<Items> viewItemInVendorById(@PathVariable @Positive int vendorId,
			@PathVariable @Positive int itemId) {
		return ResponseEntity.status(HttpStatus.OK).body(idService.viewItemByInVendorById(vendorId, itemId));
	}

	@GetMapping("/{vendorId}/items")
	public ResponseEntity<List<Items>> viewAllItemsInVendorById(@PathVariable @Positive int vendorId) {
		return ResponseEntity.status(HttpStatus.OK).body(idService.viewAllItemsInVendorById(vendorId));
	}

	@GetMapping("/{vendorId}/items/category/{itemCategory}")
	public ResponseEntity<List<Items>> viewItemListInVendorByCategory(@PathVariable @Positive int vendorId,
			@PathVariable @Pattern(regexp = "^(breakfast|lunch|dinner|sides|drinks)$", message = "Category can only be breakfast, lunch, dinner, sides or drinks.") String itemCategory) {
		return ResponseEntity.status(HttpStatus.OK).body(idService.getItemsByCategory(vendorId, itemCategory));
	}

	@GetMapping("/nearest")
	public ResponseEntity<List<Vendor>> getNearestVendors(@RequestParam @Positive long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(vendorService.getNearestVendors(userId));
	}

	@GetMapping("/user/distance")
	public ResponseEntity<Integer> distanceBetweenUserAndVendor(@RequestParam @Positive long userId,
			@RequestParam @Positive int vendorId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(vendorService.getDistanceBetweenVendorAndUser(userId, vendorId));
	}
}
