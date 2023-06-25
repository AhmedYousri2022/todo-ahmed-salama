package com.simplesystem.todo.validation;

import java.time.ZonedDateTime;

import com.simplesystem.todo.exception.BadRequestException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validation {

    public static void isValidDate(ZonedDateTime zonedDateTime) {
        if (!zonedDateTime.isAfter(ZonedDateTime.now())) {
            throw new BadRequestException("Due date should be in the future");
        }
    }
}
