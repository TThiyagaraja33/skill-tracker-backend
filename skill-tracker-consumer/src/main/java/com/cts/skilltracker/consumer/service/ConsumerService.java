package com.cts.skilltracker.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skilltracker.consumer.entity.ProfileEntity;
import com.cts.skilltracker.consumer.entity.UserEntity;
import com.cts.skilltracker.consumer.model.Profile;
import com.cts.skilltracker.consumer.model.User;
import com.cts.skilltracker.consumer.repository.ProfileRepository;
import com.cts.skilltracker.consumer.repository.UserRepository;
import com.cts.skilltracker.consumer.util.ConsumerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	UserRepository userRepo;

	public void saveProfile(String message) {
		Profile profile = ConsumerUtil.convertProfileJsontoObject(message);
		log.info(profile + "");
		ProfileEntity updated = profileRepository.save(profile.toProfile());
		log.info("Profile updated in Mongo DB for the UserId:{}", updated.getAssociateId());
	}

	public void saveUser(String message) {
		User user = ConsumerUtil.convertUserJsontoObject(message);
		log.info(user + "");
		UserEntity updated = userRepo.save(user.toUser());
		log.info("User updated in Mongo DB for the UserId:{}", updated.getUsername());
	}

	public void deleteProfile(String message) {
		Profile profile = ConsumerUtil.convertProfileJsontoObject(message);
		log.info(profile + "");
		profileRepository.delete(profile.toProfile());
		log.info("Profile deleted in Mongo DB");		
	}
}