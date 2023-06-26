package com.simplesystem.todo.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.Status;
import com.simplesystem.todo.dto.TodoResponseDto;
import com.simplesystem.todo.exception.BadRequestException;
import com.simplesystem.todo.exception.NotFoundException;
import com.simplesystem.todo.service.TodoService;
import com.simplesystem.todo.stubs.TodoResponseDtoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService service;

    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setup() {
        itemRequestDto = ItemRequestDto.builder()
                .description("todo 1")
                .status(Status.NOT_DONE)
                .dueDate(ZonedDateTime.now(ZoneId.of("Europe/Berlin")).plusDays(1)).build();
    }

    @Test
    void shouldCreateItem() throws Exception {
        given(service.addTodo(itemRequestDto))
                .willReturn(TodoResponseDtoStub.getDto());

        mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemRequestDto)))

                .andExpect(status().isOk())

                .andExpect(jsonPath("itemId").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("dueDate").exists())
                .andExpect(jsonPath("createdAt").exists());
    }

    @Test
    void shouldReturnBadRequest_when_CreatingItem() throws Exception {
        itemRequestDto.setDueDate(ZonedDateTime.now(ZoneId.of("Europe/Berlin")).minusDays(1));

        doThrow(new BadRequestException("Due date should be in the future"))
                .when(service)
                .addTodo(itemRequestDto);

        mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("Due date should be in the future"));
    }

    @Test
    void should_getIdemDetails() throws Exception {
        given(service.getItemDetails("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4"))
                .willReturn(TodoResponseDtoStub.getDto());

        mvc.perform(get("/todos/5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                            .accept(MediaType.APPLICATION_JSON))
                // we should check all fields
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").exists())
                .andExpect(jsonPath("$.description").isNotEmpty());
    }

    @Test
    void should_throw_ItemNotFound() throws Exception {
        doThrow(new NotFoundException("Item not found"))
                .when(service)
                .getItemDetails(anyString());

        mvc.perform(get("/todos/ad70855f0fc4").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("Item not found"));
    }

    @Test
    void should_changeDescription() throws Exception {
        TodoResponseDto dto = TodoResponseDtoStub.getDto();
        dto.setDescription("des");

        given(service.updateItemDescription("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4", "des"))
                .willReturn(dto);

        mvc.perform(patch("/todos/5087fb1f-8d57-46e0-9cdb-ad70855f0fc4")
                            .content("des")
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("description").isNotEmpty())
                .andExpect(jsonPath("description").value("des"));
    }

    @Test
    void should_markAsDone() throws Exception {
        TodoResponseDto dto = TodoResponseDtoStub.getDto();
        dto.setStatus(Status.DONE);
        dto.setDoneDate(ZonedDateTime.now());

        given(service.markItem("5087fb1f-8d57-46e0-9cdb-ad70855f0fc4", "DONE", null))
                .willReturn(dto);

        mvc.perform(patch("/todos/5087fb1f-8d57-46e0-9cdb-ad70855f0fc4/status/DONE")
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").isNotEmpty())
                .andExpect(jsonPath("status").value("DONE"));
    }
}
