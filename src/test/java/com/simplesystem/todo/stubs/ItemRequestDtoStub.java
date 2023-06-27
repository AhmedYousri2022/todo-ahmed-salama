package com.simplesystem.todo.stubs;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.Status;
import utils.TimeUtil;

public class ItemRequestDtoStub {

    public static ItemRequestDto getDto() {
        return ItemRequestDto.builder()
                .description("description")
                .dueDate(ZonedDateTime.now().plusDays(3))
                .status(Status.NOT_DONE).build();
    }
}
