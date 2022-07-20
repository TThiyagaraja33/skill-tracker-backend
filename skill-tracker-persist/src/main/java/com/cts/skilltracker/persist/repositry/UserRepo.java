package com.cts.skilltracker.persist.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.skilltracker.persist.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
}
