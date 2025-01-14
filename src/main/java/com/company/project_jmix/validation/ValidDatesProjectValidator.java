package com.company.project_jmix.validation;

import com.company.project_jmix.entity.Project;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidDatesProjectValidator implements ConstraintValidator<ValidDatesProject, Project> {
    @Override
    public boolean isValid(Project project, ConstraintValidatorContext context) {
        if (project == null){
            return false;
        }

        LocalDate startDate = project.getStartDate();
        LocalDate endDate = project.getEndDate();

        if (startDate == null || endDate == null) {
            return true;
        }

        return endDate.isAfter(startDate);
    }
}
