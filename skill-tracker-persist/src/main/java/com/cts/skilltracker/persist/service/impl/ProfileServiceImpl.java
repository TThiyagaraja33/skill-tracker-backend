package com.cts.skilltracker.persist.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.entity.ProfileEntity;
import com.cts.skilltracker.persist.exception.NotValidUpdateException;
import com.cts.skilltracker.persist.exception.ProfileNotFoundException;
import com.cts.skilltracker.persist.kafka.KafkaProducer;
import com.cts.skilltracker.persist.repositry.ProfileRepo;
import com.cts.skilltracker.persist.service.ProfileService;
import com.cts.skilltracker.persist.util.ProfileUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ProfileRepo profileRepo;

	@Autowired
	KafkaProducer kafkaProducer;

	@Override
	public void createProfile(ProfileRequest request) {

		log.info("Creating");
		ProfileEntity profile = profileRepo.save(request.toProfile());
		log.info("Created");
		kafkaProducer.send(ProfileUtil.convertObjectToJson(profile), true);
		log.info("Sent to Kafka Topic");
	}

	@Override
	public ResponseEntity<ProfileEntity> updateProfile(String id, ProfileRequest request) {
		log.info("Updating");
		ProfileEntity profile = profileRepo.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Not Found Profile for the Id:" + id));

		checkUpdateConstraint(profile.getUpdatedDate() != null ? profile.getUpdatedDate() : profile.getCreatedDate());

		request.getSkills().forEach(skillRequest -> {
			profile.getSkills().forEach(skill -> {
				if (skill.getSkillName().equalsIgnoreCase(skillRequest.getSkillName())) {
					skill.setExpertiseLevel(skillRequest.getExpertiseLevel());
				}
			});
		});

		profile.setUpdatedDate(new Date());
		ProfileEntity updatedProfile = profileRepo.save(profile);
		log.info("Updated");
		kafkaProducer.send(ProfileUtil.convertObjectToJson(updatedProfile), true);
		log.info("Sent to Kafka Topic");
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}
	
	@Override
	public void deleteProfile(String id) {
		log.info("Deleting");
		ProfileEntity profile = profileRepo.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Not Found Profile for the Id:" + id));
		profileRepo.delete(profile);
		log.info("Deleted");	
		
		kafkaProducer.delete(ProfileUtil.convertObjectToJson(profile));
		log.info("Sent to Kafka Topic");
	}

	private void checkUpdateConstraint(Date date) {
		Calendar updatedDate = Calendar.getInstance();
		updatedDate.setTime(date);
		updatedDate.add(Calendar.DATE, 10);
		log.info("Added 10 Days:" + updatedDate.getTime());

		Calendar today = Calendar.getInstance();
		log.info("Today's Date:" + today.getTime());

		if (updatedDate.after(today)) {
			throw new NotValidUpdateException("Not Eligible for update the Skills");
		}
	}
}