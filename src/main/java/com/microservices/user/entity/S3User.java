package com.microservices.user.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6632739919704524969L;
	
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String emailID;
	private String phoneNumber;
}
