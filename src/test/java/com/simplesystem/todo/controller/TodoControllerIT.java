package com.simplesystem.todo.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesystem.todo.dto.ItemRequestDto;
import com.simplesystem.todo.dto.Status;
import com.simplesystem.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
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
        mvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("message").value("Due date should be in the future"));
    }

}
