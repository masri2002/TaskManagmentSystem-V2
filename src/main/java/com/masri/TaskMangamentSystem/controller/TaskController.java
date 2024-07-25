package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Priority;
import com.masri.TaskMangamentSystem.entity.Status;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
import com.masri.TaskMangamentSystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;


    @Autowired
    public TaskController(TaskService taskService ) {
        this.taskService = taskService;

    }

    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        try {
            taskService.add(task);
            return new ResponseEntity<>("Task added successfully", HttpStatus.CREATED);
        } catch (DuplicateTaskExecption e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        try {
            taskService.update(task);
            return new ResponseEntity<>("Task updated successfully", HttpStatus.OK);
        } catch (TaskNotExistExecption | DuplicateTaskExecption e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int id, @RequestParam Status status) {
        try {
            taskService.updateTaskStatus(id, status);
            return new ResponseEntity<>("Task status updated successfully", HttpStatus.OK);
        } catch (TaskNotExistExecption e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<String> updatePriority(@PathVariable int id, @RequestParam Priority priority) {
        try {
            taskService.updatePriority(id, priority);
            return new ResponseEntity<>("Task priority updated successfully", HttpStatus.OK);
        } catch (TaskNotExistExecption e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {
            taskService.deleteById(id);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.NO_CONTENT);
        } catch (TaskNotExistExecption e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        try {
            Task task = taskService.getById(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotExistExecption e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/countByStatus")
    public ResponseEntity<Map<Status, Long>> countTasksByStatus(@RequestParam Status status) {
        long count = taskService.countTasksByStatus(status);
        return new ResponseEntity<>(Map.of(status, count), HttpStatus.OK);
    }

    @GetMapping("/groupByStatus")
    public ResponseEntity<Map<Status, Long>> getTasksGroupedByStatus() {
        Map<Status, Long> groupedTasks = taskService.TasksCountGroupingByStatus();
        return new ResponseEntity<>(groupedTasks, HttpStatus.OK);
    }
}
