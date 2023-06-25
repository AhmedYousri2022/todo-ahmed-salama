package com.simplesystem.todo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface TodoRepository extends CrudRepository<Todo, UUID> {

    List<Todo> findAllByStatusAndDueDateBefore(Status status, LocalDate currentDate);
    List<Todo> findAllByStatus(Status status);
    List<Todo> findAll();

    default List<Todo> getExpiredTodos(LocalDate localDate) {
        return findAllByStatusAndDueDateBefore(Status.NOT_DONE, localDate);
    }
}
