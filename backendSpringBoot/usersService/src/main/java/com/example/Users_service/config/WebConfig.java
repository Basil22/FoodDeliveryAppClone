package com.example.Users_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Apply CORS to all endpoints
				.allowedOrigins("http://localhost:4200") // Allow only your Angular app's origin
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
				.allowedHeaders("*") // Allow all headers
				.allowCredentials(true);
	}
}
