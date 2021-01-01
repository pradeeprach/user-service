package com.microservices.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user.entity.S3User;
import com.microservices.user.service.S3UserService;

@RestController
@RequestMapping("/api/s3")
public class S3UserController {

	@Autowired
	private S3UserService s3UserService;
	
	@GetMapping("/users")
	public List<S3User> getUsers() {
		return s3UserService.getAllUsers();
	}
	
	@GetMapping("/users/{user_id}")
	public S3User getUserByID(@PathVariable(name = "user_id") Long userID) {
		return s3UserService.getUserById(userID);
	}
	
	@PostMapping("/users")
	public S3User addUser(@RequestBody S3User user) {
		return s3UserService.addUser(user);
	}
	
	@DeleteMapping("/users/{user_id}")
	public boolean deleteUser(@PathVariable(name = "user_id") Long userID) {
		return s3UserService.deleteUser(userID);
	}
	
	@PutMapping("users/{user_id}")
	public S3User updateUser(@PathVariable(name = "user_id") Long userID, @RequestBody S3User user) {
		return s3UserService.updateUserDetails(userID, user);
	}
}
