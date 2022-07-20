package com.cts.skilltracker.persist.apimodel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cts.skilltracker.persist.apimodel.validator.ProfileNotBlank;
import com.cts.skilltracker.persist.entity.ProfileEntity;
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
@ProfileNotBlank
public class ProfileRequest {

    @NotNull(message = "Name should not be Null or Empty")
    @Size(min=5, max=30, message = "Name size should be between 5 & 30")
    private String name;

    @NotBlank(message = "Associate Id should not be Null or Empty")
    @Size(min=5, max=30, message = "Name size should be between 5 & 30")
    private String associateId;

    @Email(message = "Email Id is Empty or Null or Not in Valid Format")
    private String emailId;

    @NotBlank(message = "Mobile Number should not be Null or Empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number should be 10 Characters")
    private String mobileNo;

    @NotNull(message = "Invalid Skills List")
    @Valid
    private List<SkillRequest> skills;

    public ProfileEntity toProfile() {
        return ProfileEntity.builder().name(name).associateId(associateId).emailId(emailId).mobileNo(mobileNo)
                .skills(skills.stream().map(i -> i.toSkill()).collect(Collectors.toList()))
                .createdDate(new Date()).build();
    }
}