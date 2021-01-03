package com.microservices.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.user.entity.User;
import com.microservices.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ActiveProfiles("test")
class UserControllerTest {
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	private User user;
	private List<User> users;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		user = new User();
		user.setId(1L);
		user.setFirstname("firstname");
		user.setLastname("lastname");
		user.setEmailID("emailid");
		user.setPassword("password");
		user.setPhoneNumber("phoneNumber");
		user.setUsername("username");

		users = new ArrayList<>();
		users.add(user);
		when(userRepository.findAll()).thenReturn(users);
	}
	
	@Test
	void shouldGetAllUsersRecords() throws UnsupportedEncodingException, Exception {
		String response = mockMvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andReturn().getResponse()
				.getContentAsString();
		
		List<User> resultUsers = new ObjectMapper().readValue(response, new TypeReference<List<User>>(){});
		
		assertNotNull(resultUsers);
		assertNotNull(resultUsers.get(0));
		assertEquals(users.get(0).getId(), resultUsers.get(0).getId());
	}

}
