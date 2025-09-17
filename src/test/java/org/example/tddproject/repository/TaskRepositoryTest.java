package org.example.tddproject.repository;

import org.example.tddproject.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;
    @Test
    void testSaveTask(){
        //arrange
        Task task = new Task("Test task", "To do");
               // act
        Task savedTask = taskRepository.save(task);

        // assert

        assertNotNull(savedTask);
        assertEquals("Test task", savedTask.getTitle());
    }
    @Test
    void testDeleteTask(){
        //arrange
        Task task = new Task("Test to delete", "Done");

        taskRepository.save(task);

        // act
        taskRepository.delete(task);
        Optional<Task> deletedTask = taskRepository.findById(task.getId());
        assertFalse(deletedTask.isPresent());
    }

    @Test
    void tesFindAllTasks(){
        //arrange
        Task task1 = new Task("Test task 1", "To do");
        Task task2 = new Task("Test task 2", "To do");
        taskRepository.save(task1);
        taskRepository.save(task2);
        // act
       List<Task> tasks = taskRepository.findAll();

        // assert
        assertEquals(2, tasks.size());
    }

    @Test
    void testFindTaskById(){
        //arrange
        Task task = new Task("Test task", "To do");
        taskRepository.save(task);
        // act
        Optional<Task> foundTask = taskRepository.findById(task.getId());
        // assert
        assertFalse(foundTask.isEmpty());
        assertEquals(task.getId(), foundTask.get().getId());
    }

    @Test
    void testUpdateTask(){
        //arrange
        Task task = new Task("Test task", "To do");
        taskRepository.save(task);
        // act
        task.setStatus("Done");
        taskRepository.save(task);
        Optional<Task> foundTask = taskRepository.findById(task.getId());
        // assert
        assertFalse(foundTask.isEmpty());
        assertEquals("Done", foundTask.get().getStatus());
    }

}
