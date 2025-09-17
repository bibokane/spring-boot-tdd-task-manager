package org.example.tddproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tddproject.exception.TaskNotFoundException;
import org.example.tddproject.model.Task;
import org.example.tddproject.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask() throws Exception {
        //arrange
        Task task = new Task("Controller Test task", "To do");
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        //act and assert
        mockMvc.perform(post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Controller Test task"));

    }
    @Test
    void testCreateTask_InvalidInput() throws Exception {
        //arrange
        Task task = new Task("", "To do");

        // act and assert

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTask() throws Exception {
        //arrange

        Task updatedTask = new Task(1L,"Updated task", "In progress");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        String  taskJson = objectMapper.writeValueAsString(updatedTask);

        //act and assert
        mockMvc.perform(put("/tasks/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated task"));

        verify(taskService).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void testDeleteTask() throws Exception {
        //arrange
        doNothing().when(taskService).deleteTask(1L);

        //act and assert
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
        verify(taskService).deleteTask(1L);
    }

    @Test
    void testGetTaskById() throws Exception {
        //arrange
        Task task = new Task(1L,"Existing task", "To do");
        when(taskService.getTaskById(1L)).thenReturn(task);

        // act and assert
        mockMvc.perform(get("/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Existing task"));
    }

    @Test
    void testGetTaskById_TaskNotFound() throws Exception {
        // arange
        when(taskService.getTaskById(1L)).thenThrow(new TaskNotFoundException("Task with id not found"));

        // act and assert
        mockMvc.perform(get("/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(taskService).getTaskById(1L);
    }

    @Test
    void testGetAllTasks() throws Exception {
        //arrange
        List<Task> tasks = Arrays.asList(
                new Task(1L,"task1", "To do"),
                new Task(2L,"task2", "in progress")
        );
        when(taskService.getAllTasks()).thenReturn(tasks);

        // act and assert
        mockMvc.perform(get("/tasks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("task1"))
                .andExpect(jsonPath("$[1].title").value("task2"));
    }
}
