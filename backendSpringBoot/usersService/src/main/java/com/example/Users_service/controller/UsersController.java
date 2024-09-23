package com.example.Users_service.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Users_service.dto.LoginRequestDto;
import com.example.Users_service.dto.UsersDTO;
import com.example.Users_service.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	// Save user with validation
	@PostMapping("/save")
    public ResponseEntity< Map<String, Object>> createUser(@Valid @RequestBody UsersDTO user) {
        UsersDTO savedUser = usersService.saveUser(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User saved successfully");
        response.put("data", savedUser);
        
        return  ResponseEntity.ok(response);
    }

	// Get all users
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UsersDTO>> getAllUsers() {
		List<UsersDTO> userList = usersService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	// Get user by ID
	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<UsersDTO> getUserById(@PathVariable @Positive Long userId) {
		UsersDTO userDTO = usersService.getUserById(userId);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	// Update user by ID with validation
	@PutMapping("/updateUserById/{userId}")
	public ResponseEntity<UsersDTO> updateUser(@PathVariable @Positive Long userId, @Valid @RequestBody UsersDTO userDto) {
		UsersDTO updatedUser = usersService.updateUser(userId, userDto);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	// Delete user by ID
	@DeleteMapping("deleteById/{userId}")
	public ResponseEntity<String> deleteUsersById(@PathVariable @Positive Long userId) {
		usersService.deleteUser(userId);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}

	// Login by phone number and password
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		Optional<UsersDTO> userDto = usersService.getUserByPhoneNumber(loginRequestDto.getUserPhoneNumber());

		if (userDto.isPresent()) {
			UsersDTO usersDto = userDto.get();

			if (loginRequestDto.getUserPassword().equals(usersDto.getUserPassword())) {
				return new ResponseEntity<>(Collections.singletonMap("message", "Login Successfully"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Collections.singletonMap("message", "Invalid password"),
						HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(Collections.singletonMap("message", "PhoneNumber is not registered"),
					HttpStatus.NOT_FOUND);
		}
	}
}