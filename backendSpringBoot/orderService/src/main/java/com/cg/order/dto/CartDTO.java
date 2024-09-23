package com.cg.order.dto;

import java.util.Map;

public class CartDTO {
	private Long id;
	private Map<String, Integer> itemQuantities;
	
	public CartDTO() {
		
	}

	public CartDTO(Long id, Map<String, Integer> itemQuantities) {
		super();
		this.id = id;
		this.itemQuantities = itemQuantities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, Integer> getItemQuantities() {
		return itemQuantities;
	}

	public void setItemQuantities(Map<String, Integer> itemQuantities) {
		this.itemQuantities = itemQuantities;
	}

}
