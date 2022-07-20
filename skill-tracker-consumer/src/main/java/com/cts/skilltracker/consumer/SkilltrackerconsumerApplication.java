package com.cts.skilltracker.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SkilltrackerconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkilltrackerconsumerApplication.class, args);
	}
}