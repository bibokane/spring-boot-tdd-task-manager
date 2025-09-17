package org.example.tddproject.service;

import org.example.tddproject.exception.TaskNotFoundException;
import org.example.tddproject.model.Task;
import org.example.tddproject.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task updateTaskStatus(long l, String status) {
            Task tasktoUpdate = getTaskById(l);
            tasktoUpdate.setStatus(status);
            return taskRepository.save(tasktoUpdate);
    }

    public Task getTaskById(long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
       return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task taskToDelete = getTaskById(id);
        taskRepository.delete(taskToDelete);
    }

    public Task updateTask(Long l, Task updatedTask) {
        Task taskToUpdate = getTaskById(l);
        taskToUpdate.setTitle(updatedTask.getTitle());
        taskToUpdate.setStatus(updatedTask.getStatus());
        return taskRepository.save(taskToUpdate);
    }
}
