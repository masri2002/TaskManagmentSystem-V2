package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.service.ProjectService;

import com.masri.TaskMangamentSystem.service.TaskProcessor;
import com.masri.TaskMangamentSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;
    private final TaskProcessor taskProcessor;
    @Autowired
    public ProjectController(ProjectService service, TaskProcessor taskProcessor) {
        this.service = service;
        this.taskProcessor = taskProcessor;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
         service.addProject(project);
         return ResponseEntity.ok(project);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int  id) {
         service.deleteProject(id);
         return ResponseEntity.ok("Project Deleted");
    }
    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
         service.updateProject(project);
         return ResponseEntity.ok(project);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable int id){
         return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("{pId}/users")
    public ResponseEntity<?> getProjectUsers(@PathVariable int pId){
        return ResponseEntity.ok(service.getProjectUserById(pId));
    }
    @PutMapping("/{id}/process")
    public ResponseEntity<?> processProject(@PathVariable int id){
        List<Task>list=service.getProjectTaskById(id);
        taskProcessor.processAllTasks(list);
         return ResponseEntity.ok("project Tasks start Process");
    }
    @PostMapping("/{pId}/user/{uId}")

   public ResponseEntity<?> addUserToProjectById(@PathVariable int pId,@PathVariable int uId){
        service.assignUserToProject(pId,uId);
        return ResponseEntity.ok("user added to project");
   }

}
