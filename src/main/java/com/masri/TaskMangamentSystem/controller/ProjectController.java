package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.service.ProjectService;
import com.masri.TaskMangamentSystem.service.TaskProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to manage {@link Project} entity.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;
    private final TaskProcessor taskProcessor;

    /**
     * Constructs a ProjectController with the specified ProjectService and TaskProcessor.
     *
     * @param service the service layer for managing projects
     * @param taskProcessor for processing project tasks
     */
    @Autowired
    public ProjectController(ProjectService service, TaskProcessor taskProcessor) {
        this.service = service;
        this.taskProcessor = taskProcessor;
    }

    /**
     * Adds a new project.
     *
     * @param project the project to be added
     * @return the added project
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        service.addProject(project);
        return ResponseEntity.ok(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to be deleted
     * @return a message indicating the project was deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int  id) {
        service.deleteProject(id);
        return ResponseEntity.ok("Project Deleted");
    }

    /**
     * Updates an existing project.
     *
     * @param project the project to be updated
     * @return the updated project
     */
    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        service.updateProject(project);
        return ResponseEntity.ok(project);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project to retrieve
     * @return the project with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable int id){
        return ResponseEntity.ok((service.getById(id)));
    }

    /**
     * Retrieves users associated with a project.
     *
     * @param pId the ID of the project
     * @return a list of users associated with the project
     */
    @GetMapping("{pId}/users")
    public ResponseEntity<?> getProjectUsers(@PathVariable int pId){
        return ResponseEntity.ok(service.getProjectUserById(pId));
    }

    /**
     * Processes all tasks for a project.
     *
     * @param id the ID of the project
     * @return a message indicating the project tasks are being processed
     */
    @PutMapping("/{id}/process")
    public ResponseEntity<?> processProject(@PathVariable int id){
        List<Task> list = service.getProjectTaskById(id);
        taskProcessor.processAllTasks(list);
        return ResponseEntity.ok("Project tasks start process");
    }

    /**
     * Adds a user to a project.
     *
     * @param pId the ID of the project
     * @param uId the ID of the user
     * @return a message indicating the user was added to the project
     */
    @PostMapping("/{pId}/user/{uId}")
    public ResponseEntity<?> addUserToProjectById(@PathVariable int pId, @PathVariable int uId){
        service.assignUserToProject(pId, uId);
        return ResponseEntity.ok("User added to project");
    }

    /**
     * Retrieves tasks associated with a project.
     *
     * @param pId the ID of the project
     * @return a list of tasks associated with the project
     */
    @GetMapping("{pId}/task")
    public ResponseEntity<?> getProjectTasks(@PathVariable int pId){
        return ResponseEntity.ok(service.getProjectTaskById(pId));
    }
    @GetMapping("{pId}/task/status")
    public ResponseEntity<?> getTasksCountByStatus(@PathVariable int pId){
        return ResponseEntity.ok(service.countTasksStatus(pId));
    }
}
