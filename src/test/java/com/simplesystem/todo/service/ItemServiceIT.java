package com.simplesystem.todo.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.simplesystem.todo.config.ApplicationProperties;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.exception.BadRequestException;
import com.simplesystem.todo.exception.MarkNotAllowedException;
import com.simplesystem.todo.exception.NotFoundException;
import com.simplesystem.todo.model.Item;
import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.repository.TodoRepository;
import com.simplesystem.todo.stubs.TodoModelStub;
import com.simplesystem.todo.stubs.TodoRequestDtoStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.TimeUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ItemServiceIT {

    @Autowired
    private TodoRepository repository;

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private TodoService service;

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @Test
    void should_save_item() {
        TodoResponseDto responseDto = service.addTodo(TodoRequestDtoStub.getDto());

        assertThat(responseDto.getDescription(), is("description"));
    }

    @Test
    void should_Throw_ItemNotfound() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> service.updateItemDescription("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4", "new des"),
                "Item not found");

        assertThat(exception.getMessage(), is("Item not found"));
    }

    @Test
    void should_update_descritopn() {
        Item item = repository.save(TodoModelStub.getTodo());

        TodoResponseDto responseDto = service.updateItemDescription(String.valueOf(item.getId()), "new des");

        assertThat(responseDto.getDescription(), is("new des"));
    }

    @Test
    void should_get_AllNotDoneItems() {
        addTxns();

        List<TodoResponseDto> responseDto = service.getNotDoneItems(false);

        assertThat(responseDto, hasSize(3));
    }

    @Test
    void should_get_All_Items() {
        addTxns();

        List<TodoResponseDto> responseDto = service.getNotDoneItems(true);

        assertThat(responseDto, hasSize(4));
    }

    @Test
    void should_get_ItemDetails() {
        Item item = repository.save(TodoModelStub.getTodo());

        TodoResponseDto itemDetails = service.getItemDetails(String.valueOf(item.getId()));

        assertThat(itemDetails.getDescription(), is(item.getDescription()));
        assertThat(itemDetails.getStatus().name(), is(item.getStatus().name()));
        assertThat(itemDetails.getDueDate().toInstant(), is(item.getDueDate()));
        assertThat(itemDetails.getCreatedAt().toInstant(), is(item.getCreatedAt()));
    }

    @Test
    void should_Update_Expired_Items() {
        Item outDatedDueDate = TodoModelStub.getTodo();
        outDatedDueDate.setDueDate(Instant.now().minus(3, ChronoUnit.DAYS));
        Item item = repository.save(outDatedDueDate);

        service.updateExpiredTodos();

        Optional<Item> updatedItem = repository.findById(item.getId());
        assertThat(updatedItem.get().getStatus().name(), is(com.simplesystem.todo.dto.Status.PAST_DUE.name()));
    }

    @Test
    void should_Mark_DoneItem() {
        Item item = repository.save(TodoModelStub.getTodo());

        TodoResponseDto responseDto = service.markItem(String.valueOf(item.getId()), "DONE", null);

        assertThat(responseDto.getStatus().name(), is(com.simplesystem.todo.dto.Status.DONE.name()));
        assertThat(responseDto.getDoneDate().getDayOfWeek(), is(ZonedDateTime.now().getDayOfWeek()));
    }

    @Test
    void should_Mark_NotDoneItem() {
        Item item = repository.save(TodoModelStub.getTodo());
        LocalDateTime newDueDate = LocalDateTime.now().plusDays(3);

        TodoResponseDto responseDto = service.markItem(String.valueOf(item.getId()), "NOT_DONE", newDueDate);

        assertThat(responseDto.getStatus().name(), is(com.simplesystem.todo.dto.Status.NOT_DONE.name()));
        assertThat(responseDto.getDoneDate(), is(nullValue()));
        assertThat(responseDto.getDueDate(), is(ZonedDateTime.of(newDueDate, TimeUtil.TIMEZONE_BERLIN)));
    }

    @Test
    void should_Thorw_BadRequest_When_Mark_NotDoneItem() {
        Item item = repository.save(TodoModelStub.getTodo());

        BadRequestException notDone = assertThrows(
                BadRequestException.class,
                () -> service.markItem(String.valueOf(item.getId()), "NOT_DONE", null),
                "Due date should be updated");

        assertThat(notDone.getMessage(), is("Due date should be updated"));
    }

    @Test
    void should_Throw_MarkNotAllowedException() {
        Item pastDueItem = TodoModelStub.getTodo();
        pastDueItem.setStatus(Status.PAST_DUE);
        Item item = repository.save(pastDueItem);

        Exception exception = assertThrows(
                MarkNotAllowedException.class,
                () -> service.markItem(String.valueOf(item.getId()), "DONE", null),
                "Can not change PAST_DUE status");

        assertThat(exception.getMessage(), is("Can not change PAST_DUE status"));
    }

    private void addTxns() {
        Item item = TodoModelStub.getTodo();
        item.setStatus(Status.DONE);
        repository.save(item);
        repository.save(TodoModelStub.getTodo());
        repository.save(TodoModelStub.getTodo());
        repository.save(TodoModelStub.getTodo());
    }
}
