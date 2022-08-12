package com.cts.skilltracker.persist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.apimodel.ResponseDTO;
import com.cts.skilltracker.persist.apimodel.SkillRequest;
import com.cts.skilltracker.persist.entity.ProfileEntity;
import com.cts.skilltracker.persist.service.ProfileService;
import com.cts.skilltracker.persist.service.impl.ProfileServiceImpl;

public class SkillTrackerControllerTest {

	@InjectMocks
	SkillTrackerController controller;

	ProfileService profileService;

	@BeforeEach
	public void setup() throws Exception {
		profileService = Mockito.mock(ProfileServiceImpl.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createProfile() {
		ProfileRequest request = getProfileRequest();

		ResponseDTO actual = controller.createProfile(request);
		assertEquals(HttpStatus.CREATED, actual.getHttpStatus());
	}

	@Test
	public void updateProfile() {
		ProfileRequest request = getProfileRequest();

		ResponseEntity<ProfileEntity> value = new ResponseEntity<>(HttpStatus.OK);
		when(profileService.updateProfile("id", request)).thenReturn(value);

		ResponseEntity<ProfileEntity> actual = controller.updateProfile("id", request);

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test
	public void deleteProfile() {

		ResponseDTO actual = controller.deleteProfile("id");
		assertEquals(HttpStatus.ACCEPTED, actual.getHttpStatus());
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