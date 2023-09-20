package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	// custom query to find user by username
	// will use this with security setup to look for users when their info is passed in 
	// through an API request
	public User findByUsername(String username);
	
}
