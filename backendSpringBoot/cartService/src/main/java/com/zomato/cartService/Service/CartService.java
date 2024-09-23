package com.zomato.cartService.Service;

import com.zomato.cartService.Entity.Cart;
import com.zomato.cartService.Entity.CouponType;

import java.util.Map;
import java.util.Optional;

public interface CartService {

	
	    
	public   Cart addItemToCart(Long userId, Map<String, Integer> itemQuantities, Long vendorId);
	    
	  public  Cart addItemToCartByCategory(Long userId, Map<String, Integer> itemQuantities, Long vendorId, String categoryName);
	    
	  public  Cart updateCart(Long cartId, Long userId, Map<String, Integer> itemQuantities, Long vendorId);
	  
	//  public Cart updateCartByItems(Long cartId, Map<String, Integer> itemQuantities);
	    
	  public  Cart viewCartById(Long cartId);
	    
	  public  void deleteCartById(Long cartId);
	    
	   public Optional<Cart> getCartByUserId(Long userId);
	   
	   public Cart updateCartWithCoupon(Long cartId, CouponType couponType);
	   
	   public Cart addCutleryToCart(Long cartId, boolean hasCutlery);
	}


