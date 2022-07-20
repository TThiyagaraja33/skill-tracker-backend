package com.cts.skilltracker.retrieve.model;

import com.cts.skilltracker.retrieve.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLookupResponse {
	private UserEntity users;

	private String message;
}