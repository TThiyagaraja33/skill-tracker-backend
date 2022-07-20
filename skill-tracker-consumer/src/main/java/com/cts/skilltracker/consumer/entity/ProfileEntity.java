package com.cts.skilltracker.consumer.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Profile")
public class ProfileEntity {

	@Id
	private String associateId;

	private String name;

	private String emailId;

	private String mobileNo;

	private List<SkillEntity> skills;

	private Date createdDate;

	private Date updatedDate;
}