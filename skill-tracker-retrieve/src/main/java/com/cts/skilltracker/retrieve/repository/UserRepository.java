package com.cts.skilltracker.retrieve.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.skilltracker.retrieve.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
}