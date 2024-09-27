package com.fds.cart.controller;
 
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.fds.cart.entity.Cart;
import com.fds.cart.entity.CouponType;
import com.fds.cart.exception.CartNotFoundException;
import com.fds.cart.service.CartService;
 
@RestController
@CrossOrigin("*")
@RequestMapping("/api/carts")
public class CartController {
 
    @Autowired
    private CartService cartService;
 
    // View cart by cartId
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> viewCartById(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.viewCartById(cartId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    // Add items to cart
    @PostMapping("/add/{userId}/{vendorId}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, 
                                              @RequestBody Map<String, Integer> itemQuantities,
                                              @PathVariable Long vendorId) {
        Cart cart = cartService.addItemToCart(userId, itemQuantities, vendorId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
 
    // Add items by category to cart
    @PostMapping("/addByCategory/{userId}/{vendorId}/{categoryName}")
    public ResponseEntity<Cart> addItemToCartByCategory(@PathVariable Long userId, 
                                                        @RequestBody Map<String, Integer> itemQuantities,
                                                        @PathVariable Long vendorId,
                                                        @PathVariable String categoryName) {
        Cart cart = cartService.addItemToCartByCategory(userId, itemQuantities, vendorId, categoryName);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
 
    // Update items in the cart
    @PutMapping("/update/{cartId}/{userId}/{vendorId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, 
                                           @PathVariable Long userId, 
                                           @RequestBody Map<String, Integer> itemQuantities,
                                           @PathVariable Long vendorId) {
        Cart updatedCart = cartService.updateCart(cartId, userId, itemQuantities, vendorId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
//    @PutMapping("/update/{cartId}")
//    public ResponseEntity<Cart> updateCartByCartId(
//            @PathVariable Long cartId, 
//            @RequestBody Map<String, Integer> itemQuantities) {
//
//        // Update the cart using only the cartId and provided item quantities
//        Cart updatedCart = cartService.updateCartByItems(cartId, itemQuantities);
//        
//        // Return the updated cart with a success response
//        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
//    }
    @PutMapping("/update/SideOns/{cartId}/{userId}/{vendorId}")
    public ResponseEntity<Cart> updateCartBySideOns(@PathVariable Long cartId, 
                                           @PathVariable Long userId, 
                                           @RequestBody Map<String, Integer> itemQuantities,
                                           @PathVariable Long vendorId) {
        Cart updatedCart = cartService.updateCart(cartId, userId, itemQuantities, vendorId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

 
    
 
 
    // Delete cart by cartId
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartById(@PathVariable Long cartId) {
        try {
            cartService.deleteCartById(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    // View cart by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Optional<Cart> cartOptional = cartService.getCartByUserId(userId);
        if (cartOptional.isPresent()) {
            return new ResponseEntity<>(cartOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/applyCoupon/{cartId}")
    public ResponseEntity<Cart> applyCouponToCart(
            @PathVariable Long cartId,
            @RequestParam CouponType couponType) {
        Cart updatedCart = cartService.updateCartWithCoupon(cartId, couponType);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
 
    @PatchMapping("/{cartId}/cutlery")
    public ResponseEntity<Cart> updateCutlery(@PathVariable Long cartId, @RequestParam boolean hasCutlery) {
        Cart updatedCart = cartService.addCutleryToCart(cartId, hasCutlery);
        return ResponseEntity.ok(updatedCart);
    }
}