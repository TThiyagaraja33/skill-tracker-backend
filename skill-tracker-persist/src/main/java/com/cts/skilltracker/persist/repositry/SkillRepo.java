package com.cts.skilltracker.persist.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltracker.persist.entity.SkillEntity;

public interface SkillRepo extends JpaRepository<SkillEntity, Integer> {
}
