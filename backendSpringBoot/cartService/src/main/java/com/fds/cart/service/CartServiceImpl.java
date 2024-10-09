package com.fds.cart.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fds.cart.dto.ItemsDTO;
import com.fds.cart.dto.UserDTO;
import com.fds.cart.dto.VendorDTO;
import com.fds.cart.entity.Cart;
import com.fds.cart.entity.CouponType;
import com.fds.cart.exception.CartAlreadyExistsException;
import com.fds.cart.exception.CartNotFoundException;
import com.fds.cart.exception.ItemNotFoundException;
import com.fds.cart.exception.UserNotFoundException;
import com.fds.cart.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CartRepository cartRepository;

	private static final String USER_SERVICE_URL = "http://localhost:8083/api/users/getUserById";
	private static final String ITEM_SERVICE_URL = "http://localhost:8081/api";

	@Override
	public Cart addItemToCart(Long userId, Map<String, Integer> itemQuantities, Long vendorId) {
		try {
			// Fetch user details
			String userUrl = USER_SERVICE_URL + "/" + userId;
			UserDTO userDTO = restTemplate.getForObject(userUrl, UserDTO.class);
			if (userDTO == null) {
				throw new UserNotFoundException("User not found with ID: " + userId);
			}

			// Fetch vendor details
			String vendorUrl = ITEM_SERVICE_URL + "/vendor/" + vendorId;
			VendorDTO vendorDTO = restTemplate.getForObject(vendorUrl, VendorDTO.class);
			if (vendorDTO == null) {
				throw new RuntimeException("Vendor not found with ID: " + vendorId);
			}

			// Fetch distance
			Integer distance = getDistanceFromVendor(userId, vendorId);

			// Check if cart already exists for the user
			Optional<Cart> existingCartOpt = cartRepository.findByUserId(userId);
			Cart cart;
			if (existingCartOpt.isPresent()) {
				// Update existing cart
				cart = existingCartOpt.get();

				// Check if vendor matches the current vendor in the cart
				if (!cart.getVendorName().equals(vendorDTO.getVendorName())) {
					throw new RuntimeException("Cannot add items from different vendors in the same cart.");
				}

				// Update the item quantities in the cart
				for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
					cart.getItemQuantities().merge(entry.getKey(), entry.getValue(), Integer::sum);
				}
			} else {
				// Create a new cart if it doesn't exist
				cart = new Cart();
				cart.setUserId(userId);
				cart.setItemQuantities(itemQuantities);
				cart.setVendorName(vendorDTO.getVendorName());
			}

			double totalPrice = 0.0;
			Map<String, ItemsDTO> itemsDTOMap = new HashMap<>();

			// Fetch all items for the vendor
			String vendorItemsUrl = ITEM_SERVICE_URL + "/vendor/" + vendorId + "/items";
			ResponseEntity<List<ItemsDTO>> response = restTemplate.exchange(vendorItemsUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<ItemsDTO>>() {
					});

			List<ItemsDTO> itemsList = response.getBody();
			if (itemsList == null) {
				throw new RuntimeException("No items found for vendor with ID: " + vendorId);
			}

			// Create a map for quick lookup of items by itemName
			for (ItemsDTO item : itemsList) {
				itemsDTOMap.put(item.getItemName(), item);
			}

			// Calculate total price and verify items
			for (Map.Entry<String, Integer> entry : cart.getItemQuantities().entrySet()) {
				String itemName = entry.getKey();
				int quantity = entry.getValue();

				ItemsDTO itemDTO = itemsDTOMap.get(itemName);
				if (itemDTO == null) {
					throw new ItemNotFoundException("Item not found with name: " + itemName);
				}

				totalPrice += itemDTO.getPrice() * quantity;
			}

			cart.setTotalPrice(totalPrice);
			cart.updateCharges(distance.doubleValue()); // Set delivery charges and payable amount

			// Save and return the updated/created cart
			return cartRepository.save(cart);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Service unavailable or error fetching details: " + e.getMessage(), e);
		}
	}

	public Cart addItemToCartByCategory(Long userId, Map<String, Integer> itemQuantities, Long vendorId,
			String categoryName) {
		try {
			// Fetch user details
			String userUrl = USER_SERVICE_URL + "/" + userId;
			UserDTO userDTO = restTemplate.getForObject(userUrl, UserDTO.class);
			if (userDTO == null) {
				throw new UserNotFoundException("User not found with ID: " + userId);
			}

			// Fetch vendor details
			String vendorUrl = ITEM_SERVICE_URL + "/vendor/" + vendorId;
			VendorDTO vendorDTO = restTemplate.getForObject(vendorUrl, VendorDTO.class);
			if (vendorDTO == null) {
				throw new RuntimeException("Vendor not found with ID: " + vendorId);
			}

			Integer distance = getDistanceFromVendor(userId, vendorId);
			double totalPrice = 0.0;
			Map<String, ItemsDTO> itemsDTOMap = new HashMap<>();

			// Fetch all items for the vendor
			String vendorItemsUrl = ITEM_SERVICE_URL + "/vendor/" + vendorId + "/items/category/" + categoryName;
			ResponseEntity<List<ItemsDTO>> response = restTemplate.exchange(vendorItemsUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<ItemsDTO>>() {
					});

			List<ItemsDTO> itemsList = response.getBody();
			if (itemsList == null || itemsList.isEmpty()) {
				throw new RuntimeException("No items found for vendor with ID: " + vendorId);
			}

			// Filter items by category
			for (ItemsDTO item : itemsList) {
				if (item.getItemCategory() != null && item.getItemCategory().equalsIgnoreCase(categoryName)) {
					itemsDTOMap.put(item.getItemName(), item);
				}
			}

			if (itemsDTOMap.isEmpty()) {
				throw new RuntimeException("No items found in category: " + categoryName);
			}

			// Calculate total price
			for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
				String itemName = entry.getKey();
				int quantity = entry.getValue();
				ItemsDTO itemDTO = itemsDTOMap.get(itemName);
				if (itemDTO == null) {
					throw new ItemNotFoundException(
							"Item not found with name: " + itemName + " in category: " + categoryName);
				}
				totalPrice += itemDTO.getPrice() * quantity;
			}

			// Check if a cart already exists for the user
			Optional<Cart> existingCart = cartRepository.findByUserId(userId);
			if (existingCart.isPresent()) {
				// If a cart exists, prevent adding new items and throw an exception
				throw new CartAlreadyExistsException("User already has a cart. You cannot add more items.");
			} else {
				// Create and save a new cart
				Cart cart = new Cart();
				cart.setUserId(userId);
				cart.setItemQuantities(itemQuantities); // Use itemName as key in itemQuantities
				cart.setTotalPrice(totalPrice);
				cart.setVendorName(vendorDTO.getVendorName());
				cart.updateCharges(distance.doubleValue());
				return cartRepository.save(cart);
			}

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Service unavailable or error fetching details: " + e.getMessage(), e);
		}
	}

	public Cart updateCart(Long cartId, Long userId, Map<String, Integer> newItemQuantities, Long vendorId) {
		try {
			// Fetch user details
			String userUrl = USER_SERVICE_URL + "/" + userId;
			UserDTO userDTO = restTemplate.getForObject(userUrl, UserDTO.class);
			if (userDTO == null) {
				throw new UserNotFoundException("User not found with ID: " + userId);
			}

			// Fetch vendor details
			String vendorUrl = ITEM_SERVICE_URL + "/vendor/" + vendorId;
			VendorDTO vendorDTO = restTemplate.getForObject(vendorUrl, VendorDTO.class);
			if (vendorDTO == null) {
				throw new RuntimeException("Vendor not found with ID: " + vendorId);
			}

			// Fetch distance
			Integer distance = getDistanceFromVendor(userId, vendorId);

			// Fetch all items for the vendor
			String vendorItemsUrl = ITEM_SERVICE_URL + "/vendor/" + vendorId + "/items";
			ResponseEntity<List<ItemsDTO>> response = restTemplate.exchange(vendorItemsUrl, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<ItemsDTO>>() {
					});

			List<ItemsDTO> itemsList = response.getBody();
			if (itemsList == null) {
				throw new RuntimeException("No items found for vendor with ID: " + vendorId);
			}

			// Create a map for quick lookup of items by itemName
			Map<String, ItemsDTO> itemsDTOMap = new HashMap<>();
			for (ItemsDTO item : itemsList) {
				itemsDTOMap.put(item.getItemName(), item); // Use itemName as key
			}

			// Fetch existing cart
			Cart cart = cartRepository.findById(cartId)
					.orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

			// Get existing items from the cart
			Map<String, Integer> existingItemQuantities = cart.getItemQuantities();

			// Calculate new total price and merge item quantities
			double totalPrice = cart.getTotalPrice();
			for (Map.Entry<String, Integer> entry : newItemQuantities.entrySet()) {
				String itemName = entry.getKey();
				int newQuantity = entry.getValue();

				ItemsDTO itemDTO = itemsDTOMap.get(itemName);
				if (itemDTO == null) {
					throw new ItemNotFoundException("Item not found with name: " + itemName);
				}

				// Update the existing quantity or add a new item
				existingItemQuantities.merge(itemName, newQuantity, Integer::sum);

				// Update total price
				totalPrice += itemDTO.getPrice() * newQuantity;
			}

			// Update cart details
			cart.setItemQuantities(existingItemQuantities); // Save the merged item quantities
			cart.setTotalPrice(totalPrice); // Update total price
			cart.setVendorName(vendorDTO.getVendorName()); // Update vendor name if needed
			cart.updateCharges(distance.doubleValue()); // Update delivery charges and payable amount
			return cartRepository.save(cart);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Service unavailable or error fetching details: " + e.getMessage(), e);
		}
	}

//    public Cart updateCartByItems(Long cartId, Map<String, Integer> itemQuantities) {
//        try {
//            // Fetch existing cart
//            Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
//
//            // Fetch vendor details from cart
//            String vendorName = cart.getVendorName();
//            
//            // Fetch vendor details using vendor name
//            String vendorUrl = ITEM_SERVICE_URL + "/vendor/view/" + vendorName;
//            VendorDTO vendorDTO = restTemplate.getForObject(vendorUrl, VendorDTO.class);
//            if (vendorDTO == null) {
//                throw new RuntimeException("Vendor not found with name: " + vendorName);
//            }
//
//            double totalPrice = 0.0;
//            Map<String, ItemsDTO> itemsDTOMap = new HashMap<>();
//
//            // Fetch all items for the vendor
//            String vendorItemsUrl = ITEM_SERVICE_URL + "/vendor/" + vendorDTO.getVendorId() + "/items";
//            ResponseEntity<List<ItemsDTO>> response = restTemplate.exchange(
//                vendorItemsUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ItemsDTO>>() {}
//            );
//
//            List<ItemsDTO> itemsList = response.getBody();
//
//            if (itemsList == null) {
//                throw new RuntimeException("No items found for vendor with name: " + vendorName);
//            }
//
//            // Create a map for quick lookup of items by itemName
//            for (ItemsDTO item : itemsList) {
//                itemsDTOMap.put(item.getItemName(), item);  // Use itemName as key
//            }
//
//            // Update item quantities and calculate total price
//            for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
//                String itemName = entry.getKey();
//                int quantity = entry.getValue();
//
//                ItemsDTO itemDTO = itemsDTOMap.get(itemName);
//
//                if (itemDTO == null) {
//                    throw new ItemNotFoundException("Item not found with name: " + itemName);
//                }
//
//                totalPrice += itemDTO.getPrice() * quantity;
//            }
//
//            // Update cart details
//            cart.setItemQuantities(itemQuantities);  // itemQuantities now maps itemName to quantity
//            cart.setTotalPrice(totalPrice);
//            cart.updateCharges(vendorDTO.getDistance());
//            return cartRepository.save(cart);
//
//        } catch (HttpClientErrorException e) {
//            throw new RuntimeException("Service unavailable or error fetching details: " + e.getMessage(), e);
//        }
//    }

	// Delete cart by ID
	public void deleteCartById(Long cartId) {
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		if (cartOptional.isPresent()) {
			cartRepository.deleteById(cartId);
		} else {
			throw new CartNotFoundException("Cart not found with ID: " + cartId);
		}
	}

	public Optional<Cart> getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId);
	}

	@Override
	public Cart viewCartById(Long cartId) {
		// Fetch the cart by ID
		Optional<Cart> cartOptional = cartRepository.findById(cartId);

		// Check if the cart is present
		if (cartOptional.isPresent()) {
			// Return the cart if found
			return cartOptional.get();
		} else {
			// Throw an exception if the cart is not found
			throw new CartNotFoundException("Cart not found with ID: " + cartId);
		}
	}

	public Cart addCutleryToCart(Long cartId, boolean hasCutlery) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new CartNotFoundException("Cart not found with id " + cartId));
		cart.setHasCutlery(hasCutlery);
		return cartRepository.save(cart);
	}

	public Cart updateCartWithCoupon(Long cartId, CouponType couponType) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

		// CouponType couponType = CouponType.fromCode(couponCode);
		cart.setCoupon(couponType);

		// Recalculate total price after applying the coupon
		double totalPrice = cart.getPayableAmount();
		double discountedPrice = applyDiscount(totalPrice, couponType);
		cart.setDiscountedPrice(discountedPrice);

		return cartRepository.save(cart);
	}

	// Method to apply discount based on coupon
	private double applyDiscount(double totalPrice, CouponType couponType) {
		double discountValue = couponType.getDiscountValue();
		double discountedPrice = totalPrice;

		if (couponType.getCode().startsWith("DIS")) {
			// Percentage discount
			discountedPrice = totalPrice - (totalPrice * (discountValue / 100));
		} else if (couponType.getCode().startsWith("FLAT")) {
			// Flat discount
			discountedPrice = totalPrice - discountValue;
		}

		return discountedPrice;
	}

	public Integer getDistanceFromVendor(Long userId, Long vendorId) {
		String distanceUrl = "http://localhost:8081/api/vendor/user/distance/" + userId + "/" + vendorId;
		try {
			return restTemplate.getForObject(distanceUrl, Integer.class); // Assuming distance is returned as an Integer
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error fetching distance: " + e.getMessage(), e);
		}
	}

}
