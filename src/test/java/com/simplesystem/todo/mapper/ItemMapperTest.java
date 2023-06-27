package com.simplesystem.todo.mapper;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.model.Todo;
import com.simplesystem.todo.stubs.TodoModelStub;
import com.simplesystem.todo.stubs.TodoRequestDtoStub;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

class ItemMapperTest {

    private final ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

    @Test
    void should_Map_to_TodoResponse() {
        Todo todo = TodoModelStub.getTodo();
        TodoResponseDto todoResponse = mapper.toTodoResponse(todo);
        assertThat(todoResponse.getItemId(), is(todo.getId().toString()));
        assertThat(todoResponse.getDescription(), is(todo.getDescription()));
        assertThat(todoResponse.getStatus().name(), is(todo.getStatus().name()));
        assertThat(todoResponse.getCreatedAt().toInstant(), is(todo.getCreatedAt()));
    }

    @Test
    void should_Map_to_Todo() {
        ItemRequestDto dto = TodoRequestDtoStub.getDto();
        Todo todo = mapper.toTodo(dto);

        assertThat(todo.getDescription(), is(dto.getDescription()));
        assertThat(todo.getStatus().name(), is(dto.getStatus().name()));
        assertThat(todo.getDueDate(), is(dto.getDueDate().toInstant()));
        assertThat(todo.getDoneDate(), is(nullValue()));
    }
}
