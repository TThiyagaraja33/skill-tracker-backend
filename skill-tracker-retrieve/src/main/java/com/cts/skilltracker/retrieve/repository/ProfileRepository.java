package com.cts.skilltracker.retrieve.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cts.skilltracker.retrieve.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {

	List<ProfileEntity> findByNameLike(String name);
}