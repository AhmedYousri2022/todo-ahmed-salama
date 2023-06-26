package com.simplesystem.todo.service;

import java.time.Instant;
import java.time.ZonedDateTime;

import com.simplesystem.todo.config.ApplicationProperties;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.repository.TodoRepository;
import com.simplesystem.todo.stubs.TodoRequestDtoStub;
import com.simplesystem.todo.stubs.TodoResponseDtoStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository repository;

    @Mock
    private ApplicationProperties properties;

    @InjectMocks
    private TodoService service;

    @Test
    void addTodo() {
        // mock db
//        given(repository.save()).willReturn(dto);
        TodoResponseDto responseDto = service.addTodo(TodoRequestDtoStub.getDto());
        assertThat(responseDto.getDescription(), is("description"));
        assertThat(responseDto.getCreatedAt(), is(ZonedDateTime.now()));

    }

    @Test
    void getNotDoneItems() {
    }

    @Test
    void markItem() {
    }

    @Test
    void getItemDetails() {
    }

    @Test
    void updateExpiredTodos() {
    }
}
