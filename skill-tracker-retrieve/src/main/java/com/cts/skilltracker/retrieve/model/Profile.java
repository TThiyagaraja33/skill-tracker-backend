package com.cts.skilltracker.retrieve.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private String name;

    private String id;

    private String emailId;

    private String mobileNo;
    
    private List<Skill> technicalSkills;
    
    private List<Skill> nonTechnicalSkills;

//    private int htmlCssJavascript;
//
//    private int angular;
//
//    private int react;
//
//    private int spring;
//
//    private int restful;
//
//    private int hibernate;
//
//    private int git;
//
//    private int docker;
//
//    private int jenkins;
//
//    private int aws;
//
//    private int spoken;
//
//    private int communication;
//
//    private int aptitude;
}
