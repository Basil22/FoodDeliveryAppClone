package com.fds.vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages="com.fds.vendor.config")
@EnableDiscoveryClient
public class FdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FdsApplication.class, args);
	}

}
