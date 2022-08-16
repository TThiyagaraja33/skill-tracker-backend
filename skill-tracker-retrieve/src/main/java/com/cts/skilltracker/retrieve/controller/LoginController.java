package com.cts.skilltracker.retrieve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skilltracker.retrieve.entity.UserEntity;
import com.cts.skilltracker.retrieve.exception.ProfileNotFoundException;
import com.cts.skilltracker.retrieve.model.UserInput;
import com.cts.skilltracker.retrieve.model.UserLookupResponse;
import com.cts.skilltracker.retrieve.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1/user")
@RestController
@Api(value = "LoginController", tags = "Login validation")
@Slf4j
public class LoginController {

	@Autowired
	UserRepository userRepo;

	@ApiOperation(value = "Login", notes = "This API is used to validate the login credentials")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Logged in Successfully"),
			@ApiResponse(code = 400, message = "Invalid Login details") })
	@GetMapping("/name")
	@PreAuthorize("hasAuthority('ADMIN_PRIVILEGE') or hasAuthority('WRITE_PRIVILEGE') or hasAuthority('READ_PRIVILEGE')")
	public ResponseEntity<UserLookupResponse> validateUser(UserInput userInput) {
		log.info("Login Credentials= {}", userInput.toString());
		try {
			UserEntity userEntity = userRepo.findById(userInput.getUserName()).orElseThrow(
					() -> new ProfileNotFoundException("Not Found UserName for the Id:" + userInput.getUserName()));
			var encoder = new BCryptPasswordEncoder(12);
			if (!encoder.matches(userInput.getPassword(), userEntity.getPassword())) {
				return new ResponseEntity<>(new UserLookupResponse(), HttpStatus.NO_CONTENT);
			}
			var response = UserLookupResponse.builder().users(userEntity).message("Successfully returned User(s)!")
					.build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessage = "Failed to complete get all users request";
			log.info(e.toString());
			return new ResponseEntity<>(new UserLookupResponse(null, safeErrorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
}