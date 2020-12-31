package com.microservices.user.service;

import java.util.List;

import com.microservices.user.entity.S3User;

public interface S3UserService {

	List<S3User> getAllUsers();
	S3User getUserById(Long userID);
	S3User addUser(S3User user);
	boolean deleteUser(Long userID);
	S3User updateUserDetails(Long userID, S3User user);
}
