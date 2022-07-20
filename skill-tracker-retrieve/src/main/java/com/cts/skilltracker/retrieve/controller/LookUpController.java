package com.cts.skilltracker.retrieve.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltracker.retrieve.model.Profile;
import com.cts.skilltracker.retrieve.service.LookUpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/skill-tracker/api/v1/admin")
@RestController
@Api(value = "LookUpController", tags = "Skill Tracker API to manipulate the skill details of Engineer")
@Slf4j
public class LookUpController {

	@Autowired
	LookUpService lookUpService;

	@ApiOperation(value = "Gets Engineer Profile", notes = "This API is used to get the Engineer details and Skill details of the Engineer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Profile fetched Successfully"),
			@ApiResponse(code = 400, message = "Invalid Search details") })
	@GetMapping("/{criteria}/{criteriaValue}")
	@PreAuthorize("hasAuthority('ADMIN_PRIVILEGE')")
	public ResponseEntity<List<Profile>> getProfile(@PathVariable String criteria, @PathVariable String criteriaValue) {
		log.info("Criteria= {}", criteria);
		log.info("Criteria Value= {}", criteriaValue);
		try {
			List<Profile> profileList = lookUpService.getEngineerProfiles(criteria, criteriaValue);
			if (CollectionUtils.isEmpty(profileList)) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(profileList, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to complete get employees by holder request!:", e);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}