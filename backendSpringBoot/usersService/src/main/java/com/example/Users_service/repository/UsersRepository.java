package com.example.Users_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Users_service.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users , Long>{
	
	Optional<Users> findUserByUserPhoneNumber(String userPhoneNumber);
}
