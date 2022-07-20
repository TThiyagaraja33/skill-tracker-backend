package com.cts.skilltracker.consumer.model;

import com.cts.skilltracker.consumer.entity.SkillEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

    @JsonProperty("skillName")
    private String skillName;

    @JsonProperty("expertiseLevel")
    private int expertiseLevel;

    public SkillEntity toSkill() {
        return SkillEntity.builder().skillName(skillName)
                .expertiseLevel(expertiseLevel).build();
    }
}
