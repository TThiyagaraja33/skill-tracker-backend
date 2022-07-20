package com.cts.skilltracker.retrieve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SkillTrackerConfig {

	@Bean("technicalSkillSet")
	public Set<String> technicalSkillSet() {
		Set<String> unmodifiableSkillSet = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("HTML-CSS-JAVASCRIPT", "ANGULAR", "REACT", "SPRING",
						"RESTFUL", "HIBERNATE", "GIT", "DOCKER", "JENKINS", "AWS")));
		return unmodifiableSkillSet;
	}

	@Bean("nonTechnicalSkillSet")
	public Set<String> nonTechnicalSkillSet() {
		Set<String> unmodifiableSkillSet = Collections
				.unmodifiableSet(new HashSet<>(Arrays.asList("SPOKEN", "COMMUNICATION", "APTITUDE")));
		return unmodifiableSkillSet;
	}
}