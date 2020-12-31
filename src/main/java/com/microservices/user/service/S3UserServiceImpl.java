package com.microservices.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.user.entity.S3User;

@Service
public class S3UserServiceImpl implements S3UserService {
	
	@Autowired
	private S3Service s3Service;

	public List<S3User> getAllUsers() {
		return s3Service.readUsersFromS3();
	}

	public S3User getUserById(Long userID) {
		S3User user = new S3User();
		Optional<S3User> userFromList = s3Service.readUsersFromS3()
				.stream().filter(x -> x.getId().equals(userID))
				.findFirst();
		if (userFromList.isPresent()) {
			user = userFromList.get();
		}
		return user;
	}
	
	public S3User addUser(S3User user) {
		List<S3User> users = s3Service.readUsersFromS3();
		user.setId(s3Service.getID());
		users.add(user);
		s3Service.saveUserRecordsToS3(users);
		return user;
	}

	public boolean deleteUser(Long userID) {
		boolean isSuccessful = false;
		List<S3User> users = s3Service.readUsersFromS3();
		if (users.removeIf(x -> x.getId().equals(userID))) {
			isSuccessful = true;
			s3Service.saveUserRecordsToS3(users);
		}	
		return isSuccessful;
	}

	public S3User updateUserDetails(Long userID, S3User user) {
		if (deleteUser(userID)) {
			List<S3User> users = s3Service.readUsersFromS3();
			users.add(user);
			s3Service.saveUserRecordsToS3(users);
		}
		return user;
	}
}
