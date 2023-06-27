package com.simplesystem.todo.mapper;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.model.Item;
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
        Item item = TodoModelStub.getTodo();

        TodoResponseDto todoResponse = mapper.toTodoResponse(item);

        assertThat(todoResponse.getItemId(), is(item.getId().toString()));
        assertThat(todoResponse.getDescription(), is(item.getDescription()));
        assertThat(todoResponse.getStatus().name(), is(item.getStatus().name()));
        assertThat(todoResponse.getCreatedAt().toInstant(), is(item.getCreatedAt()));
    }

    @Test
    void should_Map_to_Todo() {
        ItemRequestDto dto = TodoRequestDtoStub.getDto();

        Item item = mapper.toTodo(dto);

        assertThat(item.getDescription(), is(dto.getDescription()));
        assertThat(item.getStatus().name(), is(dto.getStatus().name()));
        assertThat(item.getDueDate(), is(dto.getDueDate().toInstant()));
        assertThat(item.getDoneDate(), is(nullValue()));
    }
}
