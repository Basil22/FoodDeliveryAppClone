package com.fds.order.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fds.order.dto.Vendor;

@FeignClient(name = "vendor-service", contextId = "vendorClient")
public interface VendorClient {
	@GetMapping("/api/vendor/view/{name}")
	public ResponseEntity<Vendor> viewVendorDetailsByName(@PathVariable String name);
}
