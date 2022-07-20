package com.cts.skilltracker.retrieve.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cts.skilltracker.retrieve.entity.ProfileEntity;
import com.cts.skilltracker.retrieve.exception.ProfileNotFoundException;
import com.cts.skilltracker.retrieve.model.Profile;
import com.cts.skilltracker.retrieve.model.Skill;
import com.cts.skilltracker.retrieve.repository.ProfileRepository;
import com.cts.skilltracker.retrieve.service.LookUpService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LookUpServiceImpl implements LookUpService {

	@Autowired
	ProfileRepository profileRepo;

	@Autowired
	@Qualifier("technicalSkillSet")
	Set<String> technicalSkillSet;

	@Autowired
	@Qualifier("nonTechnicalSkillSet")
	Set<String> nonTechnicalSkillSet;

	@Override
	public List<Profile> getEngineerProfiles(String criteria, String criteriaValue) {
		log.info("Searching");
		List<ProfileEntity> profileList = null;
		if (StringUtils.isBlank(criteriaValue))
			throw new IllegalStateException("Criteria Value should be Non Null");
		switch (criteria) {
		case "Id":
			ProfileEntity profile = profileRepo.findById(criteriaValue)
					.orElseThrow(() -> new ProfileNotFoundException("Not Found Profile for the Id:" + criteriaValue));
			profileList = new ArrayList<>();
			profileList.add(profile);
			return manipulateResponse(profileList, "");
		case "Name":
			profileList = profileRepo.findByNameLike(criteriaValue);
			if (!CollectionUtils.isEmpty(profileList)) {
				return manipulateResponse(profileList, "");
			} else
				throw new ProfileNotFoundException("Profile Not Found the requested parameter");
		case "Skill":
			profileList = profileRepo.findAll();
			if (!CollectionUtils.isEmpty(profileList)) {
				return manipulateResponse(profileList, criteriaValue);
			} else
				throw new ProfileNotFoundException("Profile Not Found the requested parameter");
		default:
			throw new IllegalStateException(String.format("Unexpected Criteria: %s", criteria));
		}
	}

	private List<Profile> manipulateResponse(List<ProfileEntity> profileList, String skillName) {
		List<Profile> list = new ArrayList<>();
		profileList.stream().forEach(profile -> {
			Profile response = new Profile();
			response.setName(profile.getName());
			response.setId(profile.getAssociateId());
			response.setEmailId(profile.getEmailId());
			response.setMobileNo(profile.getMobileNo());
			if (StringUtils.isNotBlank(skillName)) {
				response.setTechnicalSkills(profile.getSkills().stream()
						.filter(check -> technicalSkillSet.contains(check.getSkillName()))
						.filter(s -> (s.getSkillName().equalsIgnoreCase(skillName) && s.getExpertiseLevel() > 10))
						.map(i -> new Skill(i.getSkillName(), i.getExpertiseLevel()))
						.sorted(Comparator.comparingInt(Skill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
				response.setNonTechnicalSkills(profile.getSkills().stream()
						.filter(check -> nonTechnicalSkillSet.contains(check.getSkillName()))
						.filter(s -> (s.getSkillName().equalsIgnoreCase(skillName) && s.getExpertiseLevel() > 10))
						.map(i -> new Skill(i.getSkillName(), i.getExpertiseLevel()))
						.sorted(Comparator.comparingInt(Skill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
			} else {
				response.setTechnicalSkills(
						profile.getSkills().stream().filter(check -> technicalSkillSet.contains(check.getSkillName()))
								.map(i -> new Skill(i.getSkillName(), i.getExpertiseLevel()))
								.sorted(Comparator.comparingInt(Skill::getExpertiseLevel).reversed())
								.collect(Collectors.toList()));
				response.setNonTechnicalSkills(profile.getSkills().stream()
						.filter(check -> nonTechnicalSkillSet.contains(check.getSkillName()))
						.map(i -> new Skill(i.getSkillName(), i.getExpertiseLevel()))
						.sorted(Comparator.comparingInt(Skill::getExpertiseLevel).reversed())
						.collect(Collectors.toList()));
			}
			list.add(response);
		});
		return list;
	}
}