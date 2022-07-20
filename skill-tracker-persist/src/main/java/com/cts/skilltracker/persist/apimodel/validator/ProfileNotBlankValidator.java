package com.cts.skilltracker.persist.apimodel.validator;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import com.cts.skilltracker.persist.apimodel.ProfileRequest;
import com.cts.skilltracker.persist.apimodel.SkillRequest;

public class ProfileNotBlankValidator implements ConstraintValidator<ProfileNotBlank, ProfileRequest> {

    @Autowired
    @Qualifier("skillSet")
    public Set<String> skillSet;

    @Override
    public boolean isValid(ProfileRequest profileRequest, ConstraintValidatorContext context) {

        if (StringUtils.isNotBlank(profileRequest.getAssociateId()) && !profileRequest.getAssociateId().startsWith("CTS")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Associate Id should start with CTS").addPropertyNode("associateId").addConstraintViolation();
            return false;
        }

        if (!CollectionUtils.isEmpty(profileRequest.getSkills())) {
            for (SkillRequest skill : profileRequest.getSkills()) {
                if (StringUtils.isNotBlank(skill.getSkillName()) && !skillSet.contains(skill.getSkillName())) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(skill.getSkillName() + " is not matched with available Technical Skills").addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
