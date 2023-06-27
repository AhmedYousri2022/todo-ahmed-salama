package com.simplesystem.todo.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.simplesystem.todo.config.ApplicationProperties;
import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.exception.BadRequestException;
import com.simplesystem.todo.exception.MarkNotAllowedException;
import com.simplesystem.todo.exception.NotFoundException;
import com.simplesystem.todo.mapper.ItemMapper;
import com.simplesystem.todo.mapper.ZonedDateTimeMapper;
import com.simplesystem.todo.model.Item;
import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.repository.TodoRepository;
import com.simplesystem.todo.validation.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.TimeUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;

    private final ApplicationProperties applicationProperties;

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    private final ZonedDateTimeMapper timeMapper = Mappers.getMapper(ZonedDateTimeMapper.class);

    @Transactional
    public TodoResponseDto addTodo(ItemRequestDto dto) {
        Validation.isValidDate(dto.getDueDate());
        Item item = repository.save(itemMapper.toTodo(dto));
        return itemMapper.toTodoResponse(item);
    }

    @Transactional
    public TodoResponseDto updateItemDescription(String itemId, String description) {
        Item item = getTodoIfExist(itemId);
        item.setDescription(description);
        Item updatedItem = repository.save(item);
        return itemMapper.toTodoResponse(updatedItem);
    }

    @Transactional
    public List<TodoResponseDto> getNotDoneItems(boolean withAllItems) {
        if (withAllItems) {
            return repository.findAll().stream().map(itemMapper::toTodoResponse).collect(Collectors.toList());
        }
        List<Item> notDoneItems = repository.findAllByStatus(Status.NOT_DONE);
        return notDoneItems.stream().map(itemMapper::toTodoResponse).collect(Collectors.toList());
    }

    @Transactional
    public TodoResponseDto markItem(String itemId, String status, LocalDateTime dueDate) {
        Item item = getTodoIfExist(itemId);
        if (Status.PAST_DUE == item.getStatus()) {
            throw new MarkNotAllowedException("Can not change PAST_DUE status");
        } else if (Status.DONE == Status.valueOf(status)) {
            item.setDoneDate(Instant.now());
        } else if (Status.NOT_DONE == Status.valueOf(status) && dueDate != null) {
            Validation.isValidDate(ZonedDateTime.of(dueDate, TimeUtil.TIMEZONE_BERLIN));
            item.setDoneDate(null);
            item.setDueDate(timeMapper.toInstant(ZonedDateTime.of(dueDate, TimeUtil.TIMEZONE_BERLIN)));
        }
        else if (Status.NOT_DONE == Status.valueOf(status) && dueDate == null) {
            throw new BadRequestException("Due date should be updated");
        }

        item.setStatus(Status.valueOf(status));
        Item updatedItem = repository.save(item);
        return itemMapper.toTodoResponse(updatedItem);
    }

    @Transactional
    public TodoResponseDto getItemDetails(String itemId) {
        Item item = getTodoIfExist(itemId);
        return itemMapper.toTodoResponse(item);
    }

    private Item getTodoIfExist(String itemId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(itemId);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Item id is not a valid format");
        }

        return repository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }


    @Transactional
    @Scheduled(cron = "${tasks.todo.cleanup.cron}")
    public void updateExpiredTodos() {
        if (!applicationProperties.isEnabled()) {
            return;
        }

        List<Item> expiredItems = repository.getExpiredTodos(Instant.now());
        log.debug("Expired Todo: {}", expiredItems.size());

        for (Item expiredItem : expiredItems) {
            expiredItem.setStatus(Status.PAST_DUE);
            log.debug("Todo '{}' expired. New state: {}", expiredItem.getId(), expiredItem.getStatus());
        }
        log.debug("task clean up finished");
    }
}
