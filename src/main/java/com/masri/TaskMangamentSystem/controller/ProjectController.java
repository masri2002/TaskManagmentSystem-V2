package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.service.ProjectService;
import com.masri.TaskMangamentSystem.service.TaskProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskProcessor taskProcessor;
     @Autowired
    public ProjectController(ProjectService projectService,TaskProcessor taskProcessor) {
        this.projectService = projectService;
         this.taskProcessor=taskProcessor;
    }

    @PostMapping("/{id}/user/{uId}")
    public ResponseEntity<?> addUser(@PathVariable int id , @PathVariable int uId){
        projectService.addUserToProjectById(id,uId);
        return ResponseEntity.ok("Done");
    }
    /**
     * Endpoint to process all tasks for a given project ID.
     *
     * @param id The ID of the project whose tasks are to be processed.
     * @return A ResponseEntity indicating the processing status.
     */
    @PutMapping("/{id}/process")
    public ResponseEntity<String> taskProcessor(@PathVariable int id) {
        try {
            var tasks = projectService.getProjectTasksById(id);
            if (tasks.isEmpty()) {
                return ResponseEntity.badRequest().body("No tasks found for project ID: " + id);
            }

            taskProcessor.processAllTasks(tasks);
            return ResponseEntity.ok("Task processing started for project ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing tasks: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}/user/{uId}")
    public ResponseEntity<?> removeUserFromProject(@PathVariable int id , @PathVariable int uId){
        projectService.removeUserFromProjectById(id,uId);
        return ResponseEntity.ok("user removed");
    }
}
