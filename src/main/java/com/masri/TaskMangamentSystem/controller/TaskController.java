package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles HTTP requests for managing tasks.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Constructs a TaskController with the given TaskService.
     *
     * @param taskService the service for task operations
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Adds a new task.
     *
     * @param task the task to add
     * @return a response indicating success
     */
    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task added");
    }

    /**
     * Deletes a task by ID.
     *
     * @param id the ID of the task to delete
     * @return a response indicating success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task deleted");
    }

    /**
     * Assigns a task to a project.
     *
     * @param tId the task ID
     * @param pId the project ID
     * @return a response indicating success
     */
    @PutMapping("/{tId}/project/{pId}")
    public ResponseEntity<String> assignTaskToProject(@PathVariable int tId, @PathVariable int pId) {
        taskService.assignTaskToProject(tId, pId);
        return ResponseEntity.status(HttpStatus.OK).body("Task assigned to project");
    }

    /**
     * Retrieves a task by ID.
     *
     * @param id the ID of the task
     * @return the task with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    /**
     * Retrieves the project associated with a task.
     *
     * @param id the task ID
     * @return the project associated with the task
     */
    @GetMapping("/{id}/project")
    public ResponseEntity<?> getProjectById(@PathVariable int id) {
        return ResponseEntity.ok(taskService.getProjectById(id));
    }

    /**
     * Retrieves a count of tasks grouped by status.
     *
     * @return a map of status counts
     */
    @GetMapping("/status")
    public ResponseEntity<?> getStatusCountForTask() {
        return ResponseEntity.ok(taskService.TasksCountGroupingByStatus());
    }
}
