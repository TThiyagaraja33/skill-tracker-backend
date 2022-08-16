package com.cts.skilltracker.persist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.apimodel.ResponseDTO;
import com.cts.skilltracker.persist.entity.ProfileEntity;
import com.cts.skilltracker.persist.service.ProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/skill-tracker/api/v1")
@RestController
@Api(value = "SkillTrackerController", tags = "Skill Tracker API to manipulate the skill details of Engineer")
@Slf4j
public class SkillTrackerController {

    @Autowired
    ProfileService profileService;

    @ApiOperation(value = "Create Engineer Profile", notes = "This API is used to persist Engineer Profile with Skill details to Database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile Created Successfully"),
            @ApiResponse(code = 400, message = "Invalid Profile details")})
    @PostMapping(value = "/engineer/add-profile")
    @PreAuthorize("hasAuthority('ADMIN_PRIVILEGE') or hasAuthority('WRITE_PRIVILEGE')")
    public ResponseDTO createProfile(@Valid @RequestBody ProfileRequest request) {
        log.info("Profile= "+request.toString());
        profileService.createProfile(request);
        return new ResponseDTO("Created", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Engineer Profile", notes = "This API is used to update Skill details of the Engineer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile updated Successfully"),
            @ApiResponse(code = 400, message = "Invalid Profile details")})
    @PutMapping(value = "/engineer/update-profile/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PRIVILEGE') or hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<ProfileEntity> updateProfile(@PathVariable String id, @Valid @RequestBody ProfileRequest request) {
        log.info("Profile= "+request.toString());
        return profileService.updateProfile(id, request);
    }
    
    @ApiOperation(value = "Delete Engineer Profile", notes = "This API is used to delete Skill details of the Engineer")
    @ApiResponses(value = {@ApiResponse(code = 202, message = "Profile deleted Successfully"),
            @ApiResponse(code = 400, message = "Invalid Profile details")})
    @DeleteMapping(value = "/engineer/delete-profile/{id}")
    @PreAuthorize("hasAuthority('ADMIN_PRIVILEGE')")
    public ResponseDTO deleteProfile(@PathVariable String id) {
        log.info("Profile Id= "+id);
        profileService.deleteProfile(id);
        return new ResponseDTO("Deleted", HttpStatus.ACCEPTED);
    }
}