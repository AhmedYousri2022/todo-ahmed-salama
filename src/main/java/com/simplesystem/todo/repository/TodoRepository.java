package com.simplesystem.todo.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface TodoRepository extends CrudRepository<Todo, UUID> {

    List<Todo> findAllByStatusAndDueDateBefore(Status status, Instant currentDate);
    List<Todo> findAllByStatus(Status status);
    List<Todo> findAll();

    default List<Todo> getExpiredTodos(Instant currentDate) {
        return findAllByStatusAndDueDateBefore(Status.NOT_DONE, currentDate);
    }
}
