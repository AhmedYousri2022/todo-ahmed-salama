package com.simplesystem.todo.repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.simplesystem.todo.model.Item;
import com.simplesystem.todo.model.Status;
import com.simplesystem.todo.stubs.ItemModelStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository repository;

    @BeforeEach
    void setup() {
        repository.save(ItemModelStub.getTodo());
        repository.save(ItemModelStub.getTodo());
        repository.save(ItemModelStub.getTodo());
        repository.save(ItemModelStub.getTodo());
        Item doneItem = ItemModelStub.getTodo();
        doneItem.setStatus(Status.DONE);
        repository.save(doneItem);
        Item dueDatePast = ItemModelStub.getTodo();
        dueDatePast.setDueDate(Instant.now().minus(4, ChronoUnit.DAYS));
        repository.save(dueDatePast);
    }

    @Test
    void should_return_expiredItems() {
        List<Item> expiredItems = repository.getExpiredTodos(Instant.now());

        assertThat(expiredItems, hasSize(1));
    }

    @Test
    void should_find_allDoneItem() {
        List<Item> expiredItems = repository.findAllByStatus(Status.DONE);

        assertThat(expiredItems, hasSize(1));
    }

    @Test
    void should_find_NotDoneItem() {
        List<Item> expiredItems = repository.findAllByStatus(Status.NOT_DONE);

        assertThat(expiredItems, hasSize(5));
    }
}
