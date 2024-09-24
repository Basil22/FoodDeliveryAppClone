package com.fds.vendor.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fds.vendor.config.UserServiceClient;
import com.fds.vendor.dto.UserDTO;
import com.fds.vendor.entity.Items;
import com.fds.vendor.entity.Vendor;
import com.fds.vendor.exception.InvalidFssaiException;
import com.fds.vendor.exception.ItemExistsException;
import com.fds.vendor.exception.ItemNotFoundException;
import com.fds.vendor.exception.VendorAlreadyExistsException;
import com.fds.vendor.exception.VendorDoesNotExistException;
import com.fds.vendor.geocoding.Coordinates;
import com.fds.vendor.geocoding.GeocodingService;
import com.fds.vendor.repository.ItemRepository;
import com.fds.vendor.repository.VendorRepository;
import com.fds.vendor.service.ItemService;
import com.fds.vendor.service.VendorService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VendorServiceImpl implements VendorService, ItemService {

	@Autowired
	private VendorRepository vendorRepo;

	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private UserServiceClient userServiceClient;

	@Autowired
	private GeocodingService geoService;

	private static final double EARTH_RADIUS_KM = 6371.0;

	@Override
	public String addVendor(Vendor vendor) {

		String vendorName = vendor.getVendorName().toLowerCase();
		String fssaiLicense = vendor.getFssaiLicenseNumber();

		if (!validateFssaiLicense(fssaiLicense)) {
			throw new InvalidFssaiException("Invalid Fssai License Number.");
		}

		if (vendorRepo.findByVendorContactNumber(vendor.getContactNumber()).isPresent()) {
			throw new VendorAlreadyExistsException("Contact number: " + vendor.getContactNumber() + " already exits.");
		}

		if (vendor.getItemList() != null) {
			for (Items item : vendor.getItemList()) {
				item.setItemName(item.getItemName().toLowerCase());
				item.setCategory(item.getCategory().toLowerCase());
			}
		}

		vendor.setVendorName(vendorName);
		vendorRepo.save(vendor);
		return "Vendor details added.";
	}

	public boolean validateFssaiLicense(String fssaiLicense) {

		String regex = "^1(0[0-9]|[12][0-9]|3[0-6])([0-9]{2})([0-9]{3})([0-9]{6})$";

		if (!fssaiLicense.matches(regex)) {
			return false;
		}

		int licenseYear = Integer.parseInt(fssaiLicense.substring(3, 5));
		int currentYear = LocalDate.now().getYear() % 100;

		if (licenseYear < currentYear - 5 || licenseYear > currentYear) {
			return false;
		}

		return true;
	}

	@Override
	public String updateVendorNameNumberAddressFssaiByName(String vendorName, Vendor vendor) {

		vendorRepo.findByVendorName(vendorName).ifPresentOrElse(v -> {
			v.setAddress(vendor.getAddress());
			v.setContactNumber(vendor.getContactNumber());
			v.setVendorName(vendor.getVendorName().toLowerCase());

			if (!validateFssaiLicense(vendor.getFssaiLicenseNumber())) {
				throw new InvalidFssaiException("Invalid FSSAI License Number.");
			}

			v.setFssaiLicenseNumber(vendor.getFssaiLicenseNumber());

			vendorRepo.save(v);
		}, () -> {
			throw new VendorDoesNotExistException(vendorName + " does not exist.");

		});

		return "Details Updated";
	}

	@Override
	public Vendor viewVendorDetailsByName(String vendorName) {
		Optional<Vendor> savedVendor = vendorRepo.findByVendorName(vendorName.toLowerCase());
		return savedVendor.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));
	}

	@Override
	public String deleteVendorByName(String vendorName) {

		Optional<Vendor> savedVendor = vendorRepo.findByVendorName(vendorName);
		if (!savedVendor.isPresent()) {
			throw new VendorDoesNotExistException(vendorName + " does not exist.");
		}

		vendorRepo.deleteByVendorName(vendorName);
		return "Vendor Removed";
	}

	@Override
	public void vendorOpenCloseToggle(String vendorName) {

		vendorRepo.findByVendorName(vendorName).ifPresentOrElse(e -> {
			e.setIsOpen(!e.isOpen());
			vendorRepo.save(e);
		}, () -> {
			throw new VendorDoesNotExistException(vendorName + " does not exist.");
		});
	}

	@Override
	public List<Vendor> viewAllVendors() {
		return vendorRepo.findAll();
	}

	// ITEMS IMPL

	@Override
	public String addItemToVendor(Items item, String vendorName) {

		Vendor savedVendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));
		if (savedVendor.getItemList() == null) {
			savedVendor.setItemList(new ArrayList<>());
		}

		item.setItemName(item.getItemName().toLowerCase());
		item.setCategory(item.getCategory().toLowerCase());
		boolean itemExists = savedVendor.getItemList().stream()
				.anyMatch(existingItem -> existingItem.getItemName().equals(item.getItemName()));

		if (itemExists) {
			throw new ItemExistsException(item.getItemName() + " already exists.");
		}

		item.setVendor(savedVendor);
		savedVendor.getItemList().add(item);

		vendorRepo.save(savedVendor);
		return "Item added.";
	}

	@Override
	public String updateItemDetailsInVendor(String vendorName, String itemName, Items item) {

		Vendor vendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));

		if (vendor.getItemList() == null || vendor.getItemList().isEmpty()) {
			throw new ItemNotFoundException(vendorName + " does not have any items.");
		}

		Items savedItem = vendor.getItemList().stream()
				.filter(existingItem -> existingItem.getItemName().equals(itemName)).findFirst()
				.orElseThrow(() -> new ItemNotFoundException(itemName + " does not exist in " + vendorName));

		savedItem.setItemName(item.getItemName());
		savedItem.setCategory(item.getCategory());
		savedItem.setDescription(item.getDescription());
		savedItem.setPrice(item.getPrice());
		savedItem.setRatings(item.getRatings());

		vendorRepo.save(vendor);

		return "Item details updated successfully";
	}

	@Override
	public String deleteItemFromVendor(String vendorName, String itemName) {
		Vendor vendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));

		if (vendor.getItemList() == null || vendor.getItemList().isEmpty()) {
			throw new ItemNotFoundException(vendorName + " does not have any items.");
		}

		Items savedItem = vendor.getItemList().stream()
				.filter(existingItem -> existingItem.getItemName().equals(itemName)).findFirst()
				.orElseThrow(() -> new ItemNotFoundException(itemName + " does not exist in " + vendorName));

		vendor.getItemList().remove(savedItem);
		vendorRepo.save(vendor);

		return "Item " + itemName + " has been deleted from " + vendorName;
	}

	@Override
	public Items viewItemInRestaurant(String vendorName, String itemName) {

		Vendor vendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));

		if (vendor.getItemList() == null || vendor.getItemList().isEmpty()) {
			throw new ItemNotFoundException(vendorName + " does not have any items.");
		}

		Items existingItem = vendor.getItemList().stream().filter(item -> item.getItemName().equals(itemName))
				.findFirst()
				.orElseThrow(() -> new ItemNotFoundException(itemName + " does not exist in " + vendorName));

		return existingItem;
	}

	@Override
	public void itemAvailabilityToggle(String vendorName, String itemName) {
		Vendor vendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));

		if (vendor.getItemList() == null || vendor.getItemList().isEmpty()) {
			throw new ItemNotFoundException(vendorName + " does not have any items.");
		}

		Items savedItem = vendor.getItemList().stream()
				.filter(existingItem -> existingItem.getItemName().equals(itemName)).findFirst()
				.orElseThrow(() -> new ItemNotFoundException(itemName + " does not exist in " + vendorName));

		savedItem.setIsAvailable(!savedItem.isAvailable());

	}

	@Override
	public List<Items> viewItemsInVendor(String vendorName) {
		Vendor vendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));

		return vendor.getItemList();
	}

	@Override
	public List<Vendor> allVendorsWithItem(String itemName) {

		List<Vendor> vendors = itemRepo.findVendorsByItemName(itemName);
		if (vendors.isEmpty()) {
			throw new ItemNotFoundException(itemName + " not found in any vendor.");
		}

		return vendors;
	}

	@Override
	public List<Items> allAvailableItemsInVendor(String vendorName) {
		Vendor vendor = vendorRepo.findByVendorName(vendorName)
				.orElseThrow(() -> new VendorDoesNotExistException(vendorName + " does not exist."));

		return vendor.getItemList().stream().filter(item -> item.isAvailable() == true).toList();
	}

	@Override
	public List<Vendor> getNearestVendors(long userId) {

		UserDTO user = userServiceClient.getByUserId(userId);
		String userAddress = user.getUserAddress();

		Coordinates userCoordinates = geoService.getCoordinates(userAddress);

		List<Vendor> allVendors = vendorRepo.findAll();

		return allVendors.stream().filter(vendor -> {
			String vendorAddress = vendor.getAddress();
			Coordinates vendorCoordinates = geoService.getCoordinates(vendorAddress);
			double distance = calculateDistance(userCoordinates, vendorCoordinates);
			return distance <= 7.0;
		}).collect(Collectors.toList());
	}

	// Calculate distance using haversine formula
	private double calculateDistance(Coordinates coord1, Coordinates coord2) {

		double lat1 = Math.toRadians(coord1.getLatitude());
		double lon1 = Math.toRadians(coord1.getLongitude());
		double lat2 = Math.toRadians(coord2.getLatitude());
		double lon2 = Math.toRadians(coord2.getLongitude());

		double dLat = lat2 - lat1;
		double dLon = lon2 - lon1;

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS_KM * c;
	}

	@Override
	public Integer getDistanceBetweenVendorAndUser(long userId, int vendorId) {

		String userAddress = userServiceClient.getByUserId(userId).getUserAddress();
		Coordinates userCoordinates = geoService.getCoordinates(userAddress);

		Vendor vendor = vendorRepo.findById(vendorId)
				.orElseThrow(() -> new VendorDoesNotExistException("Vendor does not exist."));
		Coordinates vendorCoordinates = geoService.getCoordinates(vendor.getAddress());

		return (int) calculateDistance(userCoordinates, vendorCoordinates);
	}

	@Override
	public List<Items> viewAllItems() {
		return itemRepo.findAll();
	}
}
