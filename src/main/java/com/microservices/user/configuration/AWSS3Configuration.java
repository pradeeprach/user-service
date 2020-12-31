package com.microservices.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Getter;

@Configuration
@Getter
public class AWSS3Configuration {

	@Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.access.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.bucket-name}")
    private String awsBucketName;

    @Bean(name = "awsCredentialsProvider")
    public AWSCredentialsProvider getAWSCredentials() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }
    
    @Bean
    public AmazonS3 getAmazonS3() {
    	return AmazonS3ClientBuilder
    			  .standard()
    			  .withCredentials(getAWSCredentials())
    			  .withRegion(awsRegion)
    			  .build();
    }
}
