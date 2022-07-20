package com.cts.skilltracker.retrieve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillEntity {

    private String skillName;

    private int expertiseLevel;
}