package com.microservices.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class ApplicationConfiguration {
	
	@Value("${local.users.file.path}")
	private String localFolderPath;
}
