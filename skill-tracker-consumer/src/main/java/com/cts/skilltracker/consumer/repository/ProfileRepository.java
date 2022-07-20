package com.cts.skilltracker.consumer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.skilltracker.consumer.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {
}