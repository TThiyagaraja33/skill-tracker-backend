package com.cts.skilltracker.consumer.model;

import java.io.Serializable;
import java.util.List;

import com.cts.skilltracker.consumer.entity.RoleEntity;
import com.cts.skilltracker.consumer.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("lastname")
	private String lastname;

	@JsonProperty("emailAddress")
	private String emailAddress;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;
	
	@JsonProperty("roles")
	private List<RoleEntity> roles;

	public UserEntity toUser() {
		return UserEntity.builder().username(username).firstname(firstname).lastname(lastname)
				.emailAddress(emailAddress).password(password).roles(roles).build();
	}
}