package com.microservices.user.service;

import java.util.List;

import com.microservices.user.entity.User;

public interface UserService {

	List<User> getAllUsers();
	User getUserById(Long userID);
	User addUser(User user);
	boolean deleteUser(Long userID);
	User updateUserDetails(Long userID, User user);
}
