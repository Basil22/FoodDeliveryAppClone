package com.fds.users.service;

import java.util.List;
import java.util.Optional;

import com.fds.users.dto.UsersDTO;

public interface UsersService {
	
	UsersDTO saveUser(UsersDTO usersdto);
	
	// get All Users 
	List<UsersDTO> getAllUsers();
	
	// get UserById
	UsersDTO getUserById(Long userId);
	
	// update by id 
	UsersDTO updateUser(Long userId , UsersDTO usersdto);
	
	// delete by id....	
	void deleteUser(Long userId);
	
	// get User by phoneNumber
	Optional<UsersDTO> getUserByPhoneNumber(String userPhoneNumber);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
