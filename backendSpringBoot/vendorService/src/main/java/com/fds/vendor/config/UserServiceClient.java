package com.fds.vendor.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fds.vendor.dto.UserDTO;

@FeignClient(name="user-service", url="http://localhost:8083")
public interface UserServiceClient {

	@GetMapping("/api/users/getUserById/{userId}")
	UserDTO getByUserId(@PathVariable long userId);
	
}
