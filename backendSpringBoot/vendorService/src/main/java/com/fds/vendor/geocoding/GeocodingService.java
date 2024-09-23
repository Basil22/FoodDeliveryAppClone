package com.fds.vendor.geocoding;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

	private static final String API_KEY = "a481ad67a51f46d79075510f2fb681db";
	
	public Coordinates getCoordinates(String address) {
		String uri = UriComponentsBuilder
				.fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
				.queryParam("q", address)
				.queryParam("key", API_KEY)
				.toUriString();
		
		RestTemplate restTemplate = new RestTemplate();
		GeocodingResponse response = restTemplate.getForObject(uri, GeocodingResponse.class);
		
		if(response != null && !response.getResults().isEmpty()) {
			GeocodingResponse.Result result = response.getResults().get(0);
			return new Coordinates(result.getGeometry().getLat(), result.getGeometry().getLng());
		}
		throw new RuntimeException("Unable to get coordinates for address: " + address);
	}
}
