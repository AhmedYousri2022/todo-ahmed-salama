package com.simplesystem.todo.dto;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simplesystem.todo.validation.CustomStatusSubset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemRequestDto {

    @NotNull
    //    @Min(10)
    //    @Max(255)
    private String description;

    @NotNull
    @CustomStatusSubset(anyOf = {Status.DONE, Status.NOT_DONE})
    private Status status;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Europe/Berlin")
    private ZonedDateTime dueDate;
}
