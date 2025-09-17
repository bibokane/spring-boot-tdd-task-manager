package org.example.tddproject.service;

import org.example.tddproject.exception.TaskNotFoundException;
import org.example.tddproject.model.Task;
import org.example.tddproject.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks(){
        //arrange
        List<Task> tasks = Arrays.asList(new Task(1L,"Existing task", "To do")
                , new Task(2L,"Existing task 2", "To do"));

        when(taskRepository.findAll()).thenReturn(tasks);

        // act
        List<Task> retrieveTasks = taskService.getAllTasks();

        // assert
        assertNotNull(retrieveTasks);
        assertEquals(2, retrieveTasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById(){
        //arrange
        Task task = new Task(1L,"Existing task", "To do");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //act
        Task foundTask = taskService.getTaskById(1L);

        //assert
        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getId());
        assertEquals("Existing task", foundTask.getTitle());
        assertEquals("To do", foundTask.getStatus());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_TaskNotFound(){
        //arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        //act and assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);

    }

    @Test
    void testCreateTask(){
        //arrange
        Task task = new Task("Test task", "To do");
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        //act
        Task createdTask = taskService.createTask(task);

        //assert
        assertNotNull(createdTask);
        assertEquals("Test task", createdTask.getTitle());
        assertEquals("To do", createdTask.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTaskStatus(){
        //arrange
        Task task = new Task(1L,"Existing task", "To do");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTaskStatus(1L, "In progress");

        assertNotNull(updatedTask);
        assertEquals("In progress", updatedTask.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask(){
        //arrange
        Task task = new Task(1L,"Existing task", "To do");
        Task updatedTaskData = new Task(1L,"Updated task", "Done");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);

        // act
        Task updatedTask = taskService.updateTask(1L, updatedTaskData);

        // assert
        assertNotNull(updatedTask);
        assertEquals(1L, updatedTask.getId());
        assertEquals("Updated task", updatedTask.getTitle());
        assertEquals("Done", updatedTask.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDeleteTask(){
        //arrange
        Task task = new Task(1L,"Existing task", "To do");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // act
        taskService.deleteTask(1L);

        // assert
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_TaskNotFound(){
        //arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // act and assert
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository).findById(1L);
    }
}
