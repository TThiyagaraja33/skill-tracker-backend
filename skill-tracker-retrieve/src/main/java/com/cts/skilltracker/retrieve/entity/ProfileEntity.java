package com.cts.skilltracker.retrieve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="Profile")
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