package com.fds.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fds.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByUserId(Long userId);
	// Cart findByUserId(Long userId);
}
