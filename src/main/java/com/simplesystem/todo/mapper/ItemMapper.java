package com.simplesystem.todo.mapper;

import java.util.UUID;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ZonedDateTimeMapper.class)
public interface ItemMapper {

    @Mappings({
            @Mapping(target = "itemId", source = "todo.id",  qualifiedByName = "convertUUIDtoString"),
            @Mapping(target = "description", source = "todo.description"),
            @Mapping(target = "status", source = "todo.status"),
            @Mapping(target = "createdAt", source = "todo.createdAt"),
            @Mapping(target = "dueDate", source = "todo.dueDate"),
            @Mapping(target = "doneDate", source = "todo.doneDate"),
    })
    TodoResponseDto toTodoResponse(Todo todo);

    @Mappings({
            @Mapping(target = "description", source = "itemRequestDto.description"),
            @Mapping(target = "status", source = "itemRequestDto.status"),
            @Mapping(target = "dueDate", source = "itemRequestDto.dueDate"),
            @Mapping(target = "doneDate", ignore = true),
    })
    Todo toTodo(ItemRequestDto itemRequestDto);

    @Named("convertUUIDtoString")
    static String convertUUIDtoString(UUID id) {
        return String.valueOf(id);
    }
}
