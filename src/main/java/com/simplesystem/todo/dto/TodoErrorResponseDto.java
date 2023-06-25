package com.simplesystem.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class TodoErrorResponseDto {

    private int status;

    private String message;
}
