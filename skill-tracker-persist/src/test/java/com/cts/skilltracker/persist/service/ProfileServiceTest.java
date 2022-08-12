package com.cts.skilltracker.persist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.apimodel.SkillRequest;
import com.cts.skilltracker.persist.entity.ProfileEntity;
import com.cts.skilltracker.persist.entity.SkillEntity;
import com.cts.skilltracker.persist.exception.NotValidUpdateException;
import com.cts.skilltracker.persist.exception.ProfileNotFoundException;
import com.cts.skilltracker.persist.kafka.KafkaProducer;
import com.cts.skilltracker.persist.repositry.ProfileRepo;
import com.cts.skilltracker.persist.service.impl.ProfileServiceImpl;

public class ProfileServiceTest {

	@InjectMocks
	ProfileServiceImpl service;

	ProfileRepo profileRepo;

	KafkaProducer kafkaProducer;

	@BeforeEach
	public void setup() throws Exception {
		profileRepo = Mockito.mock(ProfileRepo.class);
		kafkaProducer = Mockito.mock(KafkaProducer.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createProfileTest() {
		ProfileRequest request = getProfileRequest();

		ProfileEntity expected = new ProfileEntity();
		expected.setAssociateId("CTS12345");
		when(profileRepo.save(ArgumentMatchers.any())).thenReturn(expected);
		service.createProfile(request);
		assertTrue(true, "Saved successfully");
	}

	@Test
	public void updateProfileTest_1() {
		ProfileRequest request = getProfileRequest();

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.updateProfile("CTS12345", request);
		});

		assertEquals("Not Found Profile for the Id:CTS12345", exception.getMessage());
	}

	@Test
	public void updateProfileTest_2() {
		ProfileRequest request = getProfileRequest();

		ProfileEntity entity = getProfileEntity();
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		NotValidUpdateException exception = assertThrows(NotValidUpdateException.class, () -> {
			service.updateProfile("CTS12345", request);
		});

		assertEquals("Not Eligible for update the Skills", exception.getMessage());
	}

	@Test
	public void updateProfileTest_3() {
		ProfileRequest request = getProfileRequest();

		ProfileEntity entity = getProfileEntity();
		Calendar updatedDate = Calendar.getInstance();
		updatedDate.add(Calendar.DATE, -12);
		entity.setUpdatedDate(updatedDate.getTime());

		ProfileEntity expected = new ProfileEntity();
		expected.setAssociateId("CTS12345");

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));
		when(profileRepo.save(ArgumentMatchers.any())).thenReturn(expected);

		ResponseEntity<ProfileEntity> actual = service.updateProfile("CTS12345", request);

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void deleteProfileTest_1() {
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.deleteProfile("CTS12345");
		});

		assertEquals("Not Found Profile for the Id:CTS12345", exception.getMessage());
	}

	@Test
	public void deleteProfileTest_2() {
		ProfileEntity entity = getProfileEntity();
		Calendar updatedDate = Calendar.getInstance();
		updatedDate.add(Calendar.DATE, -12);
		entity.setUpdatedDate(updatedDate.getTime());

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		service.deleteProfile("CTS12345");

		assertTrue(true, "Profile deleted successfully");
	}

	private ProfileEntity getProfileEntity() {
		ProfileEntity entity = new ProfileEntity();
		entity.setAssociateId("CTS12345");
		entity.setCreatedDate(Calendar.getInstance().getTime());

		List<SkillEntity> skills = new ArrayList<>();
		skills.add(new SkillEntity(1, "ANGULAR", 20));
		skills.add(new SkillEntity(2, "AWS", 20));
		skills.add(new SkillEntity(3, "SPOKEN", 20));
		entity.setSkills(skills);
		return entity;
	}

	private ProfileRequest getProfileRequest() {
		ProfileRequest request = new ProfileRequest();
		request.setAssociateId("CTS12345");
		request.setEmailId("test@Test.com");
		request.setMobileNo("3242334343");
		request.setName("Test");

		List<SkillRequest> skills = new ArrayList<>();
		skills.add(new SkillRequest("ANGULAR", 20));
		skills.add(new SkillRequest("AWS", 20));
		skills.add(new SkillRequest("REACT", 20));
		skills.add(new SkillRequest("JENKINS", 20));
		skills.add(new SkillRequest("SPOKEN", 20));
		skills.add(new SkillRequest("COMMUNICATION", 20));
		request.setSkills(skills);
		return request;
	}
}