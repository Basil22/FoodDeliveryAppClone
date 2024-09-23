package com.fds.vendor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fds.vendor.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer>{
	Optional<Vendor> findByVendorName(String vendorName);
	Optional<Vendor> findByVendorContactNumber(String vendorContactNumber);
	String deleteByVendorName(String vendorName);
}
