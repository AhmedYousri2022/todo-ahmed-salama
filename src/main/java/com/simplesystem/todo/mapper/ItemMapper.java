package com.simplesystem.todo.mapper;

import java.util.UUID;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ZonedDateTimeMapper.class)
public interface ItemMapper {

    @Mappings({
            @Mapping(target = "itemId", source = "item.id",  qualifiedByName = "convertUUIDtoString"),
            @Mapping(target = "description", source = "item.description"),
            @Mapping(target = "status", source = "item.status"),
            @Mapping(target = "createdAt", source = "item.createdAt"),
            @Mapping(target = "dueDate", source = "item.dueDate"),
            @Mapping(target = "doneDate", source = "item.doneDate"),
    })
    TodoResponseDto toTodoResponse(Item item);

    @Mappings({
            @Mapping(target = "description", source = "itemRequestDto.description"),
            @Mapping(target = "status", source = "itemRequestDto.status"),
            @Mapping(target = "dueDate", source = "itemRequestDto.dueDate"),
            @Mapping(target = "doneDate", ignore = true),
    })
    Item toTodo(ItemRequestDto itemRequestDto);

    @Named("convertUUIDtoString")
    static String convertUUIDtoString(UUID id) {
        return String.valueOf(id);
    }
}
