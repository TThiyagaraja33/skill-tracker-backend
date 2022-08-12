package com.cts.skilltracker.retrieve.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.skilltracker.retrieve.entity.ProfileEntity;
import com.cts.skilltracker.retrieve.entity.SkillEntity;
import com.cts.skilltracker.retrieve.exception.ProfileNotFoundException;
import com.cts.skilltracker.retrieve.model.Profile;
import com.cts.skilltracker.retrieve.repository.ProfileRepository;

public class LookUpServiceTest {

	@InjectMocks
	LookUpServiceImpl service;

	ProfileRepository profileRepo;

	Set<String> technicalSkillSet;

	Set<String> nonTechnicalSkillSet;

	@BeforeEach
	public void setup() throws Exception {
		profileRepo = Mockito.mock(ProfileRepository.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getEngineerProfiles_Error_1() {
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			service.getEngineerProfiles("Id", "");
		});

		assertEquals("Criteria Value should be Non Null", exception.getMessage());
	}

	@Test
	public void getEngineerProfiles_Error_2() {
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
			service.getEngineerProfiles("MobileNo", "12345");
		});

		assertEquals("Unexpected Criteria: MobileNo", exception.getMessage());
	}

	@Test
	public void getEngineerProfiles_Id_Error() {

		when(profileRepo.findById("CTS12345")).thenReturn(Optional.empty());

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getEngineerProfiles("Id", "CTS12345");
		});

		assertEquals("Not Found Profile for the Id:CTS12345", exception.getMessage());
	}

	@Test
	public void getEngineerProfiles_Name_Error() {

		when(profileRepo.findByNameLike("Test")).thenReturn(null);

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getEngineerProfiles("Name", "Test");
		});

		assertEquals("Profile Not Found the requested parameter", exception.getMessage());
	}

	@Test
	public void getEngineerProfiles_Skill_Error() {

		when(profileRepo.findAll()).thenReturn(null);

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getEngineerProfiles("Skill", "AWS");
		});

		assertEquals("Profile Not Found the requested parameter", exception.getMessage());
	}

	@Test
	public void getEngineerProfiles_Id_Error_1() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(false);
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class, () -> {
			service.getEngineerProfiles("Id", "CTS12345");
		});

		assertEquals("Profile Not Found the requested parameter", exception.getMessage());
	}

	@Test
	public void getEngineerProfiles_Id() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(true);
		when(profileRepo.findById("CTS12345")).thenReturn(Optional.of(entity));

		List<Profile> actual = service.getEngineerProfiles("Id", "CTS12345");

		assertEquals("CTS12345", actual.get(0).getId());
	}

	@Test
	public void getEngineerProfiles_Name() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(true);
		List<ProfileEntity> list = new ArrayList<>();
		list.add(entity);
		when(profileRepo.findByNameLike("Test")).thenReturn(list);

		List<Profile> actual = service.getEngineerProfiles("Name", "Test");

		assertEquals("CTS12345", actual.get(0).getId());
	}
	
	@Test
	public void getEngineerProfiles_Skill() {

		ReflectionTestUtils.setField(service, "technicalSkillSet", getTechnicalSkill());
		ReflectionTestUtils.setField(service, "nonTechnicalSkillSet", getNonTechnicalSkill());

		ProfileEntity entity = getProfileEntity(true);
		List<ProfileEntity> list = new ArrayList<>();
		list.add(entity);
		when(profileRepo.findAll()).thenReturn(list);

		List<Profile> actual = service.getEngineerProfiles("Skill", "AWS");

		assertEquals("CTS12345", actual.get(0).getId());
	}

	private Set<String> getNonTechnicalSkill() {
		Set<String> unmodifiableSkillSet = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("SPOKEN", "COMMUNICATION", "APTITUDE")));
		return unmodifiableSkillSet;
	}

	private Set<String> getTechnicalSkill() {
		Set<String> unmodifiableSkillSet = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("HTML-CSS-JAVASCRIPT", "ANGULAR", "REACT", "SPRING",
						"RESTFUL", "HIBERNATE", "GIT", "DOCKER", "JENKINS", "AWS")));
		return unmodifiableSkillSet;
	}

	private ProfileEntity getProfileEntity(boolean flag) {
		ProfileEntity entity = new ProfileEntity();
		entity.setAssociateId("CTS12345");
		entity.setName("Test");
		entity.setCreatedDate(Calendar.getInstance().getTime());

		List<SkillEntity> skills = new ArrayList<>();
		if (flag) {
			skills.add(new SkillEntity("ANGULAR", 12));
			skills.add(new SkillEntity("AWS", 19));
			skills.add(new SkillEntity("JENKINS", 13));
			skills.add(new SkillEntity("SPOKEN", 10));
			skills.add(new SkillEntity("COMMUNICATION", 15));
		}
		entity.setSkills(skills);
		return entity;
	}
}