package com.fds.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fds.users.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findUserByUserPhoneNumber(String userPhoneNumber);

}
