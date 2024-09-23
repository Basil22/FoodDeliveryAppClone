package com.cg.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
	private int vendorId;
	private String vendorName;
	private String vendorContactNumber;
	private String vendorAddress;
	private boolean isOpen;
	private List<Items> itemList;
}
