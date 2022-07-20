package com.cts.skilltracker.persist.apimodel;

import com.cts.skilltracker.persist.entity.SkillEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillRequest {

    @NotBlank(message = "Skill Name should not be Null or Empty")
    private String skillName;

    @Min(value = 1, message = "Expertise Level minimum 1")
    @Max(value = 20, message = "Expertise Level maximum 20")
    private int expertiseLevel;

    public SkillEntity toSkill() {
        return SkillEntity.builder().skillName(skillName).expertiseLevel(expertiseLevel).build();
    }
}
