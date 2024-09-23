package com.cg.order.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cg.order.dto.CartDTo;

@FeignClient(name = "CartService", contextId = "CartService")
public interface CartClient {
	 @GetMapping("/api/carts/{cartId}")
	 public ResponseEntity<CartDTo> viewCartById(@PathVariable Long cartId);

}
