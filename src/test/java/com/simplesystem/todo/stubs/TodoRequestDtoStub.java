package com.simplesystem.todo.stubs;

import java.time.ZonedDateTime;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.Status;

public class TodoRequestDtoStub {

    public static ItemRequestDto getDto() {
        return ItemRequestDto.builder()
                .description("description")
                .dueDate(ZonedDateTime.now().plusDays(3))
                .status(Status.NOT_DONE).build();
    }
}
