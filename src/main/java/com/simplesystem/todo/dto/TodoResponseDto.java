package com.simplesystem.todo.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.TimeUtil;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {

    private String itemId;

    private String description;

    private Status status;

    @JsonFormat(pattern = TimeUtil.DATE_TIME_PATTERN, timezone = TimeUtil.TIMEZONE_BERLIN_STRING)
    private ZonedDateTime createdAt;

    @JsonFormat(pattern = TimeUtil.DATE_TIME_PATTERN, timezone = TimeUtil.TIMEZONE_BERLIN_STRING)
    private ZonedDateTime dueDate;

    @JsonFormat(pattern = TimeUtil.DATE_TIME_PATTERN, timezone = TimeUtil.TIMEZONE_BERLIN_STRING)
    private ZonedDateTime doneDate;
}
