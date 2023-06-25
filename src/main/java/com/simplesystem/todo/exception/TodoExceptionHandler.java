package com.simplesystem.todo.exception;

import com.simplesystem.todo.dto.TodoErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class TodoExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<TodoErrorResponseDto> handleNotFound(NotFoundException e) {
        log.error("Item is not found");
        return new ResponseEntity<>(new TodoErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TodoErrorResponseDto> handleValidationExceptions(MissingServletRequestParameterException ex) {
        log.error("MethodArgumentNotValidException", ex);
        return new ResponseEntity<>(new TodoErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<TodoErrorResponseDto> handleValidationExceptions(BadRequestException ex) {
        log.error("Bad request", ex);
        return new ResponseEntity<>(new TodoErrorResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MarkNotAllowedException.class)
    public ResponseEntity<TodoErrorResponseDto> handleValidationExceptions(MarkNotAllowedException ex) {
        log.error("not allowed to change state", ex);
        return new ResponseEntity<>(new TodoErrorResponseDto(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
                                    HttpStatus.FORBIDDEN);
    }
}
