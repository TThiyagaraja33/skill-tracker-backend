package com.cts.skilltracker.persist.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltracker.persist.entity.ProfileEntity;

public interface ProfileRepo extends JpaRepository<ProfileEntity, String> {
}
