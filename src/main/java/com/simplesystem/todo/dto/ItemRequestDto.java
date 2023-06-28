package com.simplesystem.todo.dto;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simplesystem.todo.validation.CustomStatusSubset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import utils.TimeUtil;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemRequestDto {

    @NotNull
    @Size(min = 2)
    @Size(max = 255)
    private String description;

    @NotNull
    @CustomStatusSubset(anyOf = {Status.DONE, Status.NOT_DONE})
    private Status status;

    @NotNull
    @JsonFormat(pattern = TimeUtil.DATE_TIME_PATTERN, timezone = TimeUtil.TIMEZONE_BERLIN_STRING)
    private ZonedDateTime dueDate;
}
