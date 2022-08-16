package com.cts.skilltracker.persist.apimodel;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.skilltracker.persist.entity.RoleEntity;
import com.cts.skilltracker.persist.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

	@NotBlank(message = "First Name is mandatory")
	@Size(min = 5, max = 30, message = "First Name must have within 5 -30 characters")
	private String firstname;

	@NotBlank(message = "Last Name is mandatory")
	@Size(min = 5, max = 30, message = "Last Name must have within 5 -30 characters")
	private String lastname;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String emailAddress;

	@NotBlank(message = "User Name is mandatory")
	@Size(min = 3, max = 30, message = "First Name must have within 3 -10 characters")
	private String username;

	@NotBlank(message = "Password is mandatory")
	private String password;

	@NotBlank(message = "Role is mandatory")
	private String roleName;

	public UserEntity toUser() {
		return UserEntity.builder().firstname(firstname).lastname(lastname).emailAddress(emailAddress)
				.username(username).password(hashPassword(password)).roles(setRole(roleName)).build();
	}

	private List<RoleEntity> setRole(String roleName) {
		List<RoleEntity> roles = new ArrayList<>();
		if ("Admin".equalsIgnoreCase(roleName))
			roles.add(RoleEntity.ADMIN_PRIVILEGE);
		else if ("Write".equalsIgnoreCase(roleName))
			roles.add(RoleEntity.WRITE_PRIVILEGE);
		else
			roles.add(RoleEntity.READ_PRIVILEGE);
		return roles;
	}

	private String hashPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		return encoder.encode(password);
	}
}