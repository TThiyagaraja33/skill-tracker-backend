package com.cts.skilltracker.retrieve.service;

import java.util.List;

import com.cts.skilltracker.retrieve.model.Profile;

public interface LookUpService {
	
	public List<Profile> getEngineerProfiles(String criteria, String criteriaValue);
}