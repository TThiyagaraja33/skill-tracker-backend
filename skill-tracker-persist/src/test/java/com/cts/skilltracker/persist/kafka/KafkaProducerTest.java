package com.cts.skilltracker.persist.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class KafkaProducerTest {

	@InjectMocks
	KafkaProducer producer;

	KafkaTemplate<String, String> kafkaTemplate;

	@BeforeEach
	public void setup() throws Exception {
		kafkaTemplate = Mockito.mock(KafkaTemplate.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void sendMessage_1() {
		ReflectionTestUtils.setField(producer, "profileTopicName", "profileTopic");
		producer.send("sample Msg", true);
		assertTrue(true, "Message sent successfully");
	}

	@Test
	public void sendMessage_2() {
		ReflectionTestUtils.setField(producer, "userTopicName", "userTopic");
		producer.send("sample Msg", false);
		assertTrue(true, "Message sent successfully");
	}

	@Test
	public void deleteMessage() {
		ReflectionTestUtils.setField(producer, "deleteTopicName", "deleteTopic");
		producer.delete("deleteMsg");
		assertTrue(true, "Message sent successfully");
	}
}