package com.simplesystem.todo.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoResponseDto {

    private String itemId;

    private String description;

    private Status status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Berlin")
    private ZonedDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Berlin")
    private ZonedDateTime dueDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Berlin")
    private ZonedDateTime doneDate;
}
