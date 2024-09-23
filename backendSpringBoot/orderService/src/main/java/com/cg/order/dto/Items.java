package com.cg.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Items {
	private int itemId;
	private String itemName;
	private String itemCategory;
	private double itemPrice;
	private String itemDescription;
	private double itemRatings;
	private boolean isAvailable;
	private Vendor vendor;
}
