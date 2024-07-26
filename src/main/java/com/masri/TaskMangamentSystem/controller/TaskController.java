package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.ok("Task Added");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> addTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }
    @PutMapping("/{tId}/project/{pId}")
    public ResponseEntity<?> assignTaskToProject(@PathVariable int tId , @PathVariable int pId){
        taskService.assignTaskToProject(tId,pId);
        return ResponseEntity.ok("Done");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id){

        return ResponseEntity.ok(taskService.getById(id));
    }

}
