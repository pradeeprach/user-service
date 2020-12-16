package com.microservices.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"USER\"")
@Getter
@Setter
public class User {

	@Id
    @SequenceGenerator(name = "USER_ID_GENERATOR_SEQ", sequenceName  = "USER_ID_GENERATOR_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR_SEQ")
    @Column(name = "USER_ID", nullable = false)
	private Long id;
	
	@Column(name = "FIRSTNAME", nullable = false, length = 100)
	private String firstname;
	
	@Column(name = "LASTNAME", nullable = false, length = 100)
	private String lastname;
	
	@Column(name = "EMAIL_ID", nullable = false, length = 100)
	private String emailID;
	
	@Column(name = "PHONE_NUMBER", nullable = false, length = 20)
	private String phoneNumber;
	
	@Column(name = "ADDRESS", nullable = false, length = 200)
	private String address;
}
