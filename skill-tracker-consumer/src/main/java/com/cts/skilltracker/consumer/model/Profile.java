package com.cts.skilltracker.consumer.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cts.skilltracker.consumer.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Serializable {

	@JsonProperty("name")
	private String name;

	@JsonProperty("associateId")
	private String associateId;

	@JsonProperty("emailId")
	private String emailId;

	@JsonProperty("mobileNo")
	private String mobileNo;

	@JsonProperty("skills")
	private List<Skill> skills;

	@JsonProperty("createdDate")
	private Date createdDate;

	@JsonProperty("updatedDate")
	private Date updatedDate;

	public ProfileEntity toProfile() {
		return ProfileEntity.builder().associateId(associateId).name(name)
				.emailId(emailId).mobileNo(mobileNo)
				.skills(skills.stream().map(i -> i.toSkill()).collect(Collectors.toList()))
				.createdDate(createdDate).updatedDate(updatedDate).build();
	}
}