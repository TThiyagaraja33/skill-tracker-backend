package com.cts.skilltracker.persist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SkillTrackerConfig {

    @Bean("skillSet")
    public Set<String> skillSet() {
        Set<String> unmodifiableSkillSet = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList("HTML-CSS-JAVASCRIPT", "ANGULAR", "REACT", "SPRING", "RESTFUL", "HIBERNATE",
                        "GIT", "DOCKER", "JENKINS", "AWS", "SPOKEN", "COMMUNICATION", "APTITUDE")));
        return unmodifiableSkillSet;
    }
}
