package com.microservices.user.service;

import static com.microservices.user.Constants.FILE_SEPERATOR;
import static com.microservices.user.Constants.LAST_USER_ID_FILE;
import static com.microservices.user.Constants.USERS_FILE_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.io.Files;
import com.microservices.user.configuration.AWSS3Configuration;
import com.microservices.user.configuration.ApplicationConfiguration;
import com.microservices.user.entity.S3User;

@Service
public class S3Service {
	
	private Logger logger = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Autowired
	private AWSS3Configuration awss3Configuration;
	
	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	
	@SuppressWarnings("unchecked")
	public List<S3User> readUsersFromS3() {
		List<S3User> users = new ArrayList<>();
		try {
			if (s3Client.doesObjectExist(awss3Configuration.getAwsBucketName(), USERS_FILE_NAME)) {
				S3Object s3object = s3Client.getObject(awss3Configuration.getAwsBucketName(), USERS_FILE_NAME);
				ObjectInputStream inputStream = new ObjectInputStream(s3object.getObjectContent());
				users = (List<S3User>) inputStream.readObject();
				inputStream.close();
			}
		} catch(IOException | ClassNotFoundException e) {
			logger.error("Error While reading users from s3 to local");
		}
		return users;
	}
	
	public boolean saveUserRecordsToS3(List<S3User> users) {
		boolean isSuccessful = false;
		File usersFile = new File(getFullFilePath(USERS_FILE_NAME));
		try (FileOutputStream fileOutputStream = new FileOutputStream(usersFile);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			Files.touch(usersFile);
			objectOutputStream.writeObject(users);
			uploadFile(USERS_FILE_NAME, usersFile);
		} catch (IOException e) {
			logger.error("Error While writing users list to s3");
		}
		return isSuccessful;
	}
	
	public synchronized long getID() {
		long id = 1;
		File idFile = new File(getFullFilePath(LAST_USER_ID_FILE));
		try (FileOutputStream outputStream = new FileOutputStream(idFile)) {
			Files.touch(idFile);
			if (s3Client.doesObjectExist(awss3Configuration.getAwsBucketName(), LAST_USER_ID_FILE)) {
				S3Object s3object = s3Client.getObject(awss3Configuration.getAwsBucketName(), LAST_USER_ID_FILE);
				String idString = IOUtils.toString(s3object.getObjectContent(), StandardCharsets.UTF_8);
				id = Long.parseLong(idString) + 1;
			}
			outputStream.write(String.valueOf(id).getBytes());
			uploadFile(LAST_USER_ID_FILE, idFile);
		} catch (IOException e) {
			logger.error("Error Occurred while reading/writing the id file");
		}
		return id;
	}
	
	private void uploadFile(String fileName, File file) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(awss3Configuration.getAwsBucketName(), fileName, file);
		s3Client.putObject(putObjectRequest);
	}

	private String getFullFilePath(String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append(applicationConfiguration.getLocalFolderPath());
		builder.append(FILE_SEPERATOR);
		builder.append(fileName);
		return builder.toString();
	}
}
