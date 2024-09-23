package com.example.Users_service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Users_service.dto.UsersDTO;
import com.example.Users_service.entity.Users;
import com.example.Users_service.exceptions.UserAlreadExistsException;
import com.example.Users_service.exceptions.UserNotFoundException;
import com.example.Users_service.repository.UsersRepository;
import com.example.Users_service.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UsersDTO saveUser(UsersDTO usersdto) {
		
		Optional<Users> existingUser = usersRepository.findUserByUserPhoneNumber(usersdto.getUserPhoneNumber());
		if(!existingUser.isEmpty()) {
			throw new UserAlreadExistsException("User with number: " + usersdto.getUserPhoneNumber() + " already exists.");
		}
		
		Users users = modelMapper.map(usersdto, Users.class);
		Users Saveduser = usersRepository.save(users);
		UsersDTO savedUsersDto = modelMapper.map(Saveduser, UsersDTO.class);
		return savedUsersDto;
	}

	@Override
	public List<UsersDTO> getAllUsers() {
		List<Users> savedUsers = usersRepository.findAll();
		return savedUsers.stream()
				.map(user -> modelMapper.map(user, UsersDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public UsersDTO getUserById(Long userId) {
		Users userentity = usersRepository.findById(userId).
				orElseThrow(() -> new UserNotFoundException("User with the given id  " + userId + " not found"));
		return modelMapper.map(userentity, UsersDTO.class);
	}

	@Override
	public UsersDTO updateUser(Long userId, UsersDTO usersdto) {
		Users exisistingUser = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with the given id  " + userId + " not found"));
		exisistingUser.setUserName(usersdto.getUserName());
		exisistingUser.setUserEmail(usersdto.getUserEmail());
		exisistingUser.setUserPassword(usersdto.getUserPassword());
		exisistingUser.setUserPhoneNumber(usersdto.getUserPhoneNumber());
		exisistingUser.setUserAddress(usersdto.getUserAddress());
		Users updatedUsers = usersRepository.save(exisistingUser);
		UsersDTO updatedUserDto = modelMapper.map(updatedUsers, UsersDTO.class);
		return updatedUserDto;
	}

	@Override
	public void deleteUser(Long userId) {
		usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with the given id  " + userId + " not found"));
		usersRepository.deleteById(userId);
	}

	@Override
	public Optional<UsersDTO> getUserByPhoneNumber(String userPhoneNumber) {
		Optional<Users> user = usersRepository.findUserByUserPhoneNumber(userPhoneNumber);
		return user.map(u -> modelMapper.map(u, UsersDTO.class));
	}
}
