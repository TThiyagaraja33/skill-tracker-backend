package com.cts.skilltracker.consumer.util;

import org.springframework.stereotype.Service;

import com.cts.skilltracker.consumer.model.Profile;
import com.cts.skilltracker.consumer.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerUtil {

	public static Profile convertProfileJsontoObject(String msg) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			Profile profile = mapper.readValue(msg, Profile.class);
			return profile;
		} catch (Exception ex) {
			log.error("Not able to convert to Object :{}", ex);
		}
		return null;
	}

	public static User convertUserJsontoObject(String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(message, User.class);
			return user;
		} catch (Exception ex) {
			log.error("Not able to convert to Object :{}", ex);
		}
		return null;
	}
}