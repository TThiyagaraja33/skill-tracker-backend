package com.cts.skilltracker.persist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skilltracker.persist.apimodel.UserRequest;
import com.cts.skilltracker.persist.entity.UserEntity;
import com.cts.skilltracker.persist.exception.ProfileNotFoundException;
import com.cts.skilltracker.persist.kafka.KafkaProducer;
import com.cts.skilltracker.persist.repositry.UserRepo;
import com.cts.skilltracker.persist.service.UserService;
import com.cts.skilltracker.persist.util.ProfileUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	KafkaProducer kafkaProducer;

	@Override
	public void createUser(UserRequest request) {
		log.info("User Creating");
		UserEntity user = userRepo.save(request.toUser());
		log.info("User Created");
		kafkaProducer.send(ProfileUtil.convertObjectToJson(user), false);
		log.info("Sent to Kafka");
	}

	@Override
	public void updateUser(String id, UserRequest request) {
		log.info("Updating User");
		UserEntity user = userRepo.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Not Found User for this UserName:" + id));

		UserEntity updatedUser = request.toUser();
		updatedUser.setUsername(user.getUsername());
		log.info("User Updated");
		kafkaProducer.send(ProfileUtil.convertObjectToJson(updatedUser), false);
		log.info("Sent to Kafka");
	}
}