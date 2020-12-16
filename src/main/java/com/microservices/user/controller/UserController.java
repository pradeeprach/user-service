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

import com.microservices.user.entity.User;
import com.microservices.user.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{user_id}")
	public User getUserByID(@PathVariable(name = "user_id") Long userID) {
		return userService.getUserById(userID);
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@DeleteMapping("/users/{user_id}")
	public String deleteUser(@PathVariable(name = "user_id") Long userID) {
		String result = "Delete Unsuccessful";
		if (userService.deleteUser(userID)) {
			result = "Delete Successful";
		}
		return result;
	}
	
	@PutMapping("users/{user_id}")
	public User updateUser(@PathVariable(name = "user_id") Long userID, @RequestBody User user) {
		return userService.updateUserDetails(userID, user);
	}
}
