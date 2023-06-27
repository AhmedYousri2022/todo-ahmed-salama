package com.simplesystem.todo.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.simplesystem.todo.model.Item;
import com.simplesystem.todo.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface TodoRepository extends CrudRepository<Item, UUID> {

    List<Item> findAllByStatusAndDueDateBefore(Status status, Instant currentDate);
    List<Item> findAllByStatus(Status status);
    List<Item> findAll();

    default List<Item> getExpiredTodos(Instant currentDate) {
        return findAllByStatusAndDueDateBefore(Status.NOT_DONE, currentDate);
    }
}
