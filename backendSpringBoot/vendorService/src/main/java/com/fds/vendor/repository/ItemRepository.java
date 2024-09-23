package com.fds.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fds.vendor.entity.Items;
import com.fds.vendor.entity.Vendor;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer>{

	@Query("SELECT i.vendor FROM Items i WHERE i.itemName= :itemName")
	List<Vendor> findVendorsByItemName(@Param("itemName") String itemName);
	
	List<Items> findByIsAvailableTrue();
 }
