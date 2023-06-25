package com.simplesystem.todo.mapper;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.Status;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.model.Todo;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-25T20:58:41+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
public class ItemMapperImpl implements ItemMapper {

    private final ZonedDateTimeMapper zonedDateTimeMapper = Mappers.getMapper( ZonedDateTimeMapper.class );

    @Override
    public TodoResponseDto toTodoResponse(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoResponseDto todoResponseDto = new TodoResponseDto();

        todoResponseDto.setItemId( ItemMapper.convertUUIDtoString( todo.getId() ) );
        todoResponseDto.setCreatedAt( zonedDateTimeMapper.toZonedDateTime( todo.getCreatedAt() ) );
        todoResponseDto.setDueDate( zonedDateTimeMapper.toZonedDateTime( todo.getDueDate() ) );
        todoResponseDto.setDescription( todo.getDescription() );
        todoResponseDto.setDoneDate( zonedDateTimeMapper.toZonedDateTime( todo.getDoneDate() ) );
        todoResponseDto.setStatus( statusToStatus( todo.getStatus() ) );

        return todoResponseDto;
    }

    @Override
    public Todo toTodo(ItemRequestDto itemRequestDto) {
        if ( itemRequestDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setDescription( itemRequestDto.getDescription() );
        todo.setDueDate( zonedDateTimeMapper.toInstant( itemRequestDto.getDueDate() ) );
        todo.setStatus( statusToStatus1( itemRequestDto.getStatus() ) );

        return todo;
    }

    protected Status statusToStatus(com.simplesystem.todo.model.Status status) {
        if ( status == null ) {
            return null;
        }

        Status status1;

        switch ( status ) {
            case DONE: status1 = Status.DONE;
            break;
            case NOT_DONE: status1 = Status.NOT_DONE;
            break;
            case PAST_DUE: status1 = Status.PAST_DUE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return status1;
    }

    protected com.simplesystem.todo.model.Status statusToStatus1(Status status) {
        if ( status == null ) {
            return null;
        }

        com.simplesystem.todo.model.Status status1;

        switch ( status ) {
            case DONE: status1 = com.simplesystem.todo.model.Status.DONE;
            break;
            case NOT_DONE: status1 = com.simplesystem.todo.model.Status.NOT_DONE;
            break;
            case PAST_DUE: status1 = com.simplesystem.todo.model.Status.PAST_DUE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return status1;
    }
}
