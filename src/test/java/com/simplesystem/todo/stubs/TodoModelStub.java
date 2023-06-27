package com.simplesystem.todo.stubs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.model.Todo;

public class TodoModelStub {

    public static Todo getTodo() {
        return Todo.builder()
                .id(UUID.fromString("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4"))
                .description("des")
                .dueDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .status(Status.NOT_DONE)
                .createdAt(Instant.now()).build();
    }
}
