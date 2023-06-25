package com.simplesystem.todo.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.simplesystem.todo.dto.Status;

public class StatusValidator implements ConstraintValidator<CustomStatusSubset, Status> {

    private Status[] statuses;

    @Override
    public void initialize(CustomStatusSubset constraintAnnotation) {
        this.statuses = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Status status, ConstraintValidatorContext constraintValidatorContext) {
        return status == null || Arrays.asList(statuses).contains(status);
    }
}
