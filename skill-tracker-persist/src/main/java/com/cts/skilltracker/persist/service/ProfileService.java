package com.cts.skilltracker.persist.service;

import org.springframework.http.ResponseEntity;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.entity.ProfileEntity;

public interface ProfileService {

    public void createProfile(ProfileRequest request);

    public ResponseEntity<ProfileEntity> updateProfile(String id, ProfileRequest request);
    
    public void deleteProfile(String id);
}
