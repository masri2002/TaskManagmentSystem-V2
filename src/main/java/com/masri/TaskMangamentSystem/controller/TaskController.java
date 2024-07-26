package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing tasks in the Task Management System.
 * Provides endpoints for adding, deleting, assigning tasks to projects, and retrieving tasks.
 * @author ahmad almasri
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructs a TaskController with the specified TaskService.
     *
     * @param taskService the service used to interact with task data
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Adds a new task.
     *
     * @param task the task to be added
     * @return a response indicating the task was added
     */
    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task Added");
    }

    /**
     * Deletes a task by its unique identifier.
     *
     * @param id the unique identifier of the task to be deleted
     * @return a response indicating the task was deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted");
    }

    /**
     * Assigns a task to a project.
     *
     * @param tId the unique identifier of the task
     * @param pId the unique identifier of the project
     * @return a response indicating the task was assigned to the project
     */
    @PutMapping("/{tId}/project/{pId}")
    public ResponseEntity<?> assignTaskToProject(@PathVariable int tId, @PathVariable int pId) {
        taskService.assignTaskToProject(tId, pId);
        return ResponseEntity.status(HttpStatus.OK).body("Done");
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id the unique identifier of the task
     * @return the task with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {
        return ResponseEntity. status(HttpStatus.FOUND).body(taskService.getById(id));
    }

    /**
     * Retrieves the project associated with a task by the task's unique identifier.
     *
     * @param id the unique identifier of the task
     * @return the project associated with the specified task ID
     */
    @GetMapping("/{id}/project")
    public ResponseEntity<?> getProjectById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(taskService.getProjectById(id));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatusCountForTask() {
        return ResponseEntity.status(HttpStatus.FOUND).body(taskService.TasksCountGroupingByStatus());
    }
}
