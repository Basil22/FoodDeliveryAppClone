package com.fds.users.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fds.users.dto.LoginRequestDto;
import com.fds.users.dto.UsersDTO;
import com.fds.users.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Save user with validation
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody UsersDTO user) {
		UsersDTO savedUser = usersService.saveUser(user);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "User saved successfully");
		response.put("data", savedUser);

		return ResponseEntity.ok(response);
	}

	// Get all users
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UsersDTO>> getAllUsers() {
		List<UsersDTO> userList = usersService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	// Get user by ID
	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<UsersDTO> getUserById(@PathVariable Long userId) {
		UsersDTO userDTO = usersService.getUserById(userId);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	// Update user by ID with validation
	@PutMapping("/updateUserById/{userId}")
	public ResponseEntity<UsersDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody UsersDTO userDto) {
		UsersDTO updatedUser = usersService.updateUser(userId, userDto);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	// Delete user by ID
	@DeleteMapping("deleteById/{userId}")
	public ResponseEntity<String> deleteUsersById(@PathVariable Long userId) {
		usersService.deleteUser(userId);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
		// Fetch the user by phone number
		Optional<UsersDTO> userDto = usersService.getUserByPhoneNumber(loginRequestDto.getUserPhoneNumber());

		// Check if user exists
		if (userDto.isPresent()) {
			UsersDTO usersDto = userDto.get();

			// Compare the provided password with the stored hashed password
			if (passwordEncoder.matches(loginRequestDto.getUserPassword(), usersDto.getUserPassword())) {
				return new ResponseEntity<>(Collections.singletonMap("message", "Login Successfully"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Collections.singletonMap("message", "Invalid password"),
						HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(Collections.singletonMap("message", "Phone number is not registered"),
					HttpStatus.NOT_FOUND);
		}
	}

}
