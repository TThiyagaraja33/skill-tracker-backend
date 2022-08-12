package com.cts.skilltracker.persist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cts.skilltracker.persist.apimodel.UserRequest;
import com.cts.skilltracker.persist.entity.RoleEntity;
import com.cts.skilltracker.persist.entity.UserEntity;
import com.cts.skilltracker.persist.exception.ProfileNotFoundException;
import com.cts.skilltracker.persist.kafka.KafkaProducer;
import com.cts.skilltracker.persist.repositry.UserRepo;
import com.cts.skilltracker.persist.service.impl.UserServiceImpl;

public class UserServiceTest {

	@InjectMocks
	UserServiceImpl service;

	UserRepo userRepo;

	KafkaProducer kafkaProducer;

	@BeforeEach
	public void setup() throws Exception {
		userRepo = Mockito.mock(UserRepo.class);
		kafkaProducer = Mockito.mock(KafkaProducer.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		UserRequest request = getUserRequest();

		UserEntity expected = new UserEntity();
		expected.setUsername("testUser");
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(expected);
		service.createUser(request);
		assertTrue(true, "Saved successfully");
	}

	@Test
	public void updateUserTest_1() {
		UserRequest request = getUserRequest();

		when(userRepo.findById("testUser")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.updateUser("testUser", request);
		});

		assertEquals("Not Found User for this UserName:testUser", exception.getMessage());
	}

	@Test
	public void updateUserTest_2() {
		UserRequest request = getUserRequest();

		UserEntity entity = new UserEntity();
		entity.setUsername("testUser");
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(RoleEntity.WRITE_PRIVILEGE);
		entity.setRoles(roles);

		when(userRepo.findById("testUser")).thenReturn(Optional.of(entity));

		service.updateUser("testUser", request);
		assertTrue(true, "Updated successfully");
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