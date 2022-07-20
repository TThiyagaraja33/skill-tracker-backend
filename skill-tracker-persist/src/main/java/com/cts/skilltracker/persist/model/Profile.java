package com.cts.skilltracker.persist.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private String name;

    private String associateId;

    private String emailId;

    private String mobileNo;

    private List<Skill> skills;

    private Date createdDate;

    private Date updatedDate;
}
