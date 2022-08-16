package com.cts.skilltracker.persist.util;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.apimodel.SkillRequest;
import com.cts.skilltracker.persist.apimodel.UserRequest;
import com.cts.skilltracker.persist.entity.RoleEntity;
import com.cts.skilltracker.persist.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        ProfileRequest request = new ProfileRequest();
        request.setName("Thiyagu");
        request.setAssociateId("CTS12345");
        request.setEmailId("Thiyagu@test.com");
        request.setMobileNo("1234567890");

        SkillRequest skill1 = new SkillRequest("HTML-CSS-JAVASCRIPT", 5);
        SkillRequest skill2 = new SkillRequest("ANGULAR", 5);
        SkillRequest skill3 = new SkillRequest("REACT", 5);
        SkillRequest skill4 = new SkillRequest("SPRING", 5);
        SkillRequest skill5 = new SkillRequest("RESTFUL", 5);
        SkillRequest skill6 = new SkillRequest("HIBERNATE", 5);
        SkillRequest skill7 = new SkillRequest("GIT", 5);
        SkillRequest skill8 = new SkillRequest("DOCKER", 5);
        SkillRequest skill9 = new SkillRequest("JENKINS", 5);
        SkillRequest skill10 = new SkillRequest("AWS", 5);

        SkillRequest skill11 = new SkillRequest("SPOKEN", 5);
        SkillRequest skill12 = new SkillRequest("COMMUNICATION", 5);
        SkillRequest skill13 = new SkillRequest("APTITUDE", 5);

        request.setSkills(Arrays.asList(skill1, skill2, skill3, skill4, skill5, skill6, skill7, skill8, skill9, skill10, skill11, skill12, skill13));

        ObjectMapper mapper = new ObjectMapper();
        try {
            String str = mapper.writeValueAsString(request);
            System.out.println(str);
        } catch (Exception e) {

        }
        
        UserRequest user = new UserRequest("firstname", "lastname", "emailAddress", "username", "password", "Write");
        UserEntity entity = user.toUser();
        try {
            String str = mapper.writeValueAsString(entity);
            System.out.println(str);
        } catch (Exception e) {

        }

        Date updatedDate = new Date(2022, 06, 1);

        Date date = new Date(2022, 06, 12);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println("Today's Date:"+date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(updatedDate);
        System.out.println("Before Added 10 Days:"+calendar.getTime());
        calendar.add(Calendar.DATE, 10);
        System.out.println("Added 10 Days:"+calendar.getTime());

        if(calendar.after(cal)) {
            System.out.println("In");
        } else
            System.out.println("Out");



    }
}
