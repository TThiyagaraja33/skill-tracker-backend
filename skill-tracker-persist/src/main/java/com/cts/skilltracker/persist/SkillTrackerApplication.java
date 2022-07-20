package com.cts.skilltracker.persist;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class SkillTrackerApplication {

	@Value("${topic.name.producer}")
	private String profileTopicName;

	@Value("${topic.user.producer}")
	private String userTopicName;
	
	@Value("${topic.delete.producer}")
	private String deleteTopicName;

	public static void main(String[] args) {
		SpringApplication.run(SkillTrackerApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		System.out.println("Spring boot application running in IST timezone :" + new Date());
	}

	@Bean
	public NewTopic profileTopic() {
		return TopicBuilder.name(profileTopicName).build();
	}

	@Bean
	public NewTopic userTopic() {
		return TopicBuilder.name(userTopicName).build();
	}
	
	@Bean
	public NewTopic deleteTopic() {
		return TopicBuilder.name(deleteTopicName).build();
	}
}