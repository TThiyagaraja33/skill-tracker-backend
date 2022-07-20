package com.cts.skilltracker.persist.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

	@Value("${topic.name.producer}")
	private String profileTopicName;

	@Value("${topic.user.producer}")
	private String userTopicName;
	
	@Value("${topic.delete.producer}")
	private String deleteTopicName;

	private final KafkaTemplate<String, String> kafkaTemplate;

	@Async
	public void send(String message, boolean profileFlag) {
		log.info("Payload : {}", message);
		if (profileFlag)
			kafkaTemplate.send(profileTopicName, message);
		else
			kafkaTemplate.send(userTopicName, message);
		log.info("Payload posted");
	}
	
	@Async
	public void delete(String message) {
		log.info("Payload : {}", message);
		kafkaTemplate.send(deleteTopicName, message);
		log.info("Payload posted");
	}
}
