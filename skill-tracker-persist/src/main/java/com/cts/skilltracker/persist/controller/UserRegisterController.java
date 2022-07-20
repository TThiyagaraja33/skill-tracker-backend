package com.cts.skilltracker.persist.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltracker.persist.apimodel.ResponseDTO;
import com.cts.skilltracker.persist.apimodel.UserRequest;
import com.cts.skilltracker.persist.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1/user")
@RestController
@Api(value = "UserRegisterController", tags = "User Registeration")
@Slf4j
public class UserRegisterController {
	
	@Autowired
	UserService userService;

	@ApiOperation(value = "Create User Profile", notes = "This API is used to persist User Profile")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Created Successfully"),
			@ApiResponse(code = 400, message = "Invalid User details") })
	@PostMapping(value = "/createUser")
	public ResponseDTO createProfile(@Valid @RequestBody UserRequest request) {
		log.info("User= " + request.toString());
		userService.createUser(request);
		return new ResponseDTO("Created", HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update User Profile", notes = "This API is used to update User details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User updated Successfully"),
			@ApiResponse(code = 400, message = "Invalid User details") })
	@PutMapping(value = "/updateUser/{id}")
	public ResponseDTO updateProfile(@PathVariable String id, @Valid @RequestBody UserRequest request) {
		log.info("User= " + request.toString());
		userService.updateUser(id, request);
		return new ResponseDTO("Updated", HttpStatus.OK);
	}
}