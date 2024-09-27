package com.fds.order.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fds.order.dto.CartDTO;

@FeignClient(name = "CartService", contextId = "CartService")
public interface CartClient {
	@GetMapping("/api/carts/{cartId}")
	public ResponseEntity<CartDTO> viewCartById(@PathVariable Long cartId);

}
