package com.cts.skilltracker.retrieve.controller;

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

import com.cts.skilltracker.retrieve.model.Profile;
import com.cts.skilltracker.retrieve.model.Skill;
import com.cts.skilltracker.retrieve.service.LookUpService;
import com.cts.skilltracker.retrieve.service.impl.LookUpServiceImpl;

public class LookUpControllerTest {

	@InjectMocks
	LookUpController controller;

	LookUpService lookUpService;

	@BeforeEach
	public void setup() throws Exception {
		lookUpService = Mockito.mock(LookUpServiceImpl.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProfile() {
		Profile profile = getProfileRequest();
		List<Profile> list = new ArrayList<>();
		list.add(profile);
		when(lookUpService.getEngineerProfiles("Id", "CTS12345")).thenReturn(list);

		ResponseEntity<List<Profile>> actual = controller.getProfile("Id", "CTS12345");

		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	public void getProfile_Error() {
		
		when(lookUpService.getEngineerProfiles("Id", "CTS12345")).thenReturn(null);

		ResponseEntity<List<Profile>> actual = controller.getProfile("Id", "CTS12345");

		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}

	private Profile getProfileRequest() {
		Profile request = new Profile();
		request.setId("CTS12345");
		request.setEmailId("test@Test.com");
		request.setMobileNo("3242334343");
		request.setName("Test");

		List<Skill> skills = new ArrayList<>();
		skills.add(new Skill("ANGULAR", 20));
		skills.add(new Skill("AWS", 20));
		skills.add(new Skill("REACT", 20));
		skills.add(new Skill("JENKINS", 20));
		skills.add(new Skill("SPOKEN", 20));
		skills.add(new Skill("COMMUNICATION", 20));
		request.setTechnicalSkills(skills);
		return request;
	}
}