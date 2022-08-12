package com.cts.skilltracker.retrieve.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.skilltracker.retrieve.entity.UserEntity;
import com.cts.skilltracker.retrieve.model.UserInput;
import com.cts.skilltracker.retrieve.model.UserLookupResponse;
import com.cts.skilltracker.retrieve.repository.UserRepository;

public class LoginControllerTest {

	@InjectMocks
	LoginController controller;

	UserRepository userRepo;

	@BeforeEach
	public void setup() throws Exception {
		userRepo = Mockito.mock(UserRepository.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void validateUserTest() {
		UserInput userInput = new UserInput();
		userInput.setUserName("username");
		userInput.setPassword("password");

		UserEntity request = getUserRequest();
		Optional<UserEntity> value = Optional.of(request);
		when(userRepo.findById("username")).thenReturn(value);

		ResponseEntity<UserLookupResponse> actual = controller.validateUser(userInput);

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void validateUser_Error_1() {
		UserInput userInput = new UserInput();
		userInput.setUserName("username");
		userInput.setPassword("password");

		when(userRepo.findById("username")).thenReturn(Optional.empty());

		ResponseEntity<UserLookupResponse> actual = controller.validateUser(userInput);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
	}

	@Test
	public void validateUser_Error_2() {
		UserInput userInput = new UserInput();
		userInput.setUserName("username");
		userInput.setPassword("wrongPassword");

		UserEntity request = getUserRequest();
		Optional<UserEntity> value = Optional.of(request);
		when(userRepo.findById("username")).thenReturn(value);

		ResponseEntity<UserLookupResponse> actual = controller.validateUser(userInput);

		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}

	private UserEntity getUserRequest() {
		UserEntity request = new UserEntity();
		request.setFirstname("FirstName");
		request.setLastname("LastName");
		request.setEmailAddress("test@test.com");
		request.setUsername("username");
		request.setPassword("$2a$12$2UmXfMXMm.w2SoMUDFUxZuuzcVaF01ZbYgae.fie/GHfHCmgbGDWO");
		return request;
	}
}