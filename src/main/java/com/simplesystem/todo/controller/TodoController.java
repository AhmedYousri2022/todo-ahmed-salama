package com.simplesystem.todo.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
@Api(value = "Todo service", tags = {"Todo"})
public class TodoController {

    private final TodoService service;

    @PostMapping
    @ApiOperation(value = "Add new Item")
    public TodoResponseDto addTodo(@Valid @RequestBody ItemRequestDto itemRequestDto) {
        return service.addTodo(itemRequestDto);
    }

    @PatchMapping("/{itemId}")
    @ApiOperation(value = "Change a description of an item")
    public TodoResponseDto updateItemDescription(@NotNull @PathVariable String itemId, @Valid @RequestBody String description) {
        return service.updateItemDescription(itemId,description);
    }

    @PatchMapping("/{itemId}/status/{status}")
    @ApiOperation(value = "Change status of  an item to done/not-done")
    public TodoResponseDto markItem(@Valid @PathVariable String itemId,
                                    @PathVariable String status,
                                    @RequestParam(required = false) LocalDateTime dueDate) {

        return service.markItem(itemId, status, dueDate);
    }

    @GetMapping
    @ApiOperation(value = "get all items that are not done (with an option to retrieve all items)")
    public List<TodoResponseDto> getNotDoneItems(@RequestParam(defaultValue = "false") boolean withAllItem) {
        return service.getNotDoneItems(withAllItem);
    }

    @GetMapping(path = "/{itemId}")
    @ApiOperation(value = "get item details")
    public TodoResponseDto getItemDetails(@Valid @PathVariable String itemId) {
        return service.getItemDetails(itemId);
    }
}
