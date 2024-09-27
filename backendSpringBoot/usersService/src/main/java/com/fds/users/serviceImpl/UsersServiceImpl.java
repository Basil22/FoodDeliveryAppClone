package com.fds.users.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fds.users.dto.UsersDTO;
import com.fds.users.entity.Users;
import com.fds.users.exception.UserNotFoundException;
import com.fds.users.repository.UsersRepository;
import com.fds.users.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UsersDTO saveUser(UsersDTO usersdto) {

		// convert in to user entity to save user in to repository...

		Users users = modelMapper.map(usersdto, Users.class);
		users.setUserPassword(passwordEncoder.encode(users.getUserPassword()));

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

				orElseThrow(() -> new UserNotFoundException("User with the give id  " + userId + " not found"));

		return modelMapper.map(userentity, UsersDTO.class);

	}

	@Override
	public UsersDTO updateUser(Long userId, UsersDTO usersdto) {

		Users exisistingUser = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with the give id  " + userId + " not found"));

		exisistingUser.setUserName(usersdto.getUserName());
		exisistingUser.setUserEmail(usersdto.getUserEmail());
		exisistingUser.setUserPassword(passwordEncoder.encode(usersdto.getUserPassword()));
		exisistingUser.setUserPhoneNumber(usersdto.getUserPhoneNumber());
		exisistingUser.setUserAddress(usersdto.getUserAddress());
		Users updatedUsers = usersRepository.save(exisistingUser);
		// convert updated user to userDto

		UsersDTO updatedUserDto = modelMapper.map(updatedUsers, UsersDTO.class);

		return updatedUserDto;
	}

	@Override
	public void deleteUser(Long userId) {

		usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with the give id  " + userId + " not found"));

		usersRepository.deleteById(userId);

	}

	@Override
	public Optional<UsersDTO> getUserByPhoneNumber(String userPhoneNumber) {

		Optional<Users> user = usersRepository.findUserByUserPhoneNumber(userPhoneNumber);

		return user.map(u -> modelMapper.map(u, UsersDTO.class));

	}

}
