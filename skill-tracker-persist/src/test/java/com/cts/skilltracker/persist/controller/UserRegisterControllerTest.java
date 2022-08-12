package com.cts.skilltracker.persist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.cts.skilltracker.persist.apimodel.ResponseDTO;
import com.cts.skilltracker.persist.apimodel.UserRequest;
import com.cts.skilltracker.persist.service.UserService;
import com.cts.skilltracker.persist.service.impl.UserServiceImpl;

public class UserRegisterControllerTest {

	@InjectMocks
	UserRegisterController controller;

	UserService userService;

	@BeforeEach
	public void setup() throws Exception {
		userService = Mockito.mock(UserServiceImpl.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		UserRequest request = getUserRequest();

		ResponseDTO actual = controller.createUser(request);

		assertEquals(HttpStatus.CREATED, actual.getHttpStatus());
	}

	@Test
	public void updateUserTest() {
		UserRequest request = getUserRequest();

		ResponseDTO actual = controller.updateUser("testUser", request);

		assertEquals(HttpStatus.OK, actual.getHttpStatus());
	}

	private UserRequest getUserRequest() {
		UserRequest request = new UserRequest();
		request.setFirstname("FirstName");
		request.setLastname("LastName");
		request.setEmailAddress("test@test.com");
		request.setUsername("testUser");
		request.setPassword("testPassword");
		return request;
	}
}