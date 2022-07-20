package com.cts.skilltracker.consumer.kafka;

import com.cts.skilltracker.consumer.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

	@Value("${topic.name.consumer}")
	private String profileTopicName;
	
	@Value("${topic.user.consumer}")
	private String userTopicName;

	@Autowired
	private ConsumerService consumerService;

	@KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
	public void consumeMessage(ConsumerRecord<String, String> payload) {
		log.info("Topic: {}", profileTopicName);
		log.info("payload: {}", payload.value());
		consumerService.saveProfile(payload.value());
	}
	
	@KafkaListener(topics = "${topic.user.consumer}", groupId = "group_id")
	public void consumeMessage1(ConsumerRecord<String, String> payload) {
		log.info("Topic: {}", userTopicName);
		log.info("payload: {}", payload.value());
		consumerService.saveUser(payload.value());
	}
	
	@KafkaListener(topics = "${topic.delete.consumer}", groupId = "group_id")
	public void consumeMessage2(ConsumerRecord<String, String> payload) {
		log.info("Topic: {}", profileTopicName);
		log.info("payload: {}", payload.value());
		consumerService.deleteProfile(payload.value());
	}
}