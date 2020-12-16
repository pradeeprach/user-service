package com.microservices.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.user.entity.User;
import com.microservices.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long userID) {
		User user = null;
		Optional<User> optionalUser = userRepository.findById(userID);
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		return user;
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}

	public boolean deleteUser(Long userID) {
		userRepository.deleteById(userID);
		return !userRepository.existsById(userID);
	}

	public User updateUserDetails(Long userID, User user) {
		User updatedUser = null;
		if (userRepository.existsById(userID) && user.getId().equals(userID)) {
			updatedUser = userRepository.save(user);
		}
		return updatedUser;
	}
}
