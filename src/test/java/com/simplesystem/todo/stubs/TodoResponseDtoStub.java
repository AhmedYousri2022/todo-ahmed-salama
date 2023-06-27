package com.simplesystem.todo.stubs;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.simplesystem.todo.dto.Status;
import com.simplesystem.todo.dto.TodoResponseDto;
import utils.TimeUtil;

public class TodoResponseDtoStub {

    public static TodoResponseDto getDto() {
        return TodoResponseDto.builder()
                .createdAt(ZonedDateTime
                                   .of(LocalDateTime.of(2023, 12, 12, 12, 12, 12),
                                       TimeUtil.TIMEZONE_BERLIN))
                .description("description")
                .doneDate(null)
                .dueDate(ZonedDateTime.now().plusDays(3))
                .itemId("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                .status(Status.NOT_DONE).build();
    }
}
