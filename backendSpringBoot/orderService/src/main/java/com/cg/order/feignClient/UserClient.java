package com.cg.order.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.order.dto.User;
import com.cg.order.exception.RecordNotFoundException;

@FeignClient(name = "Users-service", contextId = "userClient")
public interface UserClient {
	@GetMapping("/api/users/getUserById/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) throws RecordNotFoundException;

}
