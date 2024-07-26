package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.Status;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.service.ProjectService;
import com.masri.TaskMangamentSystem.service.TaskProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * @return the added project with a CREATED status
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        service.addProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to be deleted
     * @return a message indicating the project was deleted or a NOT FOUND status if it doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable int id) {
       service.deleteProject(id);
            return ResponseEntity.ok("Project deleted successfully.");

    }

    /**
     * Updates an existing project.
     *
     * @param project the project to be updated
     * @return the updated project with an OK status or NOT FOUND if the project doesn't exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable int id, @RequestBody Project project) {
       service.updateProject(project);
       return ResponseEntity.ok(project);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project to retrieve
     * @return the project with the specified ID or a NOT FOUND status if it doesn't exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable int id) {
        Project project = service.getById(id);
            return ResponseEntity.ok(project);
    }

    /**
     * Retrieves users associated with a project.
     *
     * @param pId the ID of the project
     * @return a list of users associated with the project or NOT FOUND if the project doesn't exist
     */
    @GetMapping("/{pId}/users")
    public ResponseEntity<?> getProjectUsers(@PathVariable int pId) {
        List<User> users = service.getProjectUserById(pId);
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found for this project.");

        }
    }

    /**
     * Processes all tasks for a project.
     *
     * @param id the ID of the project
     * @return a message indicating the project tasks are being processed or NOT FOUND if the project doesn't exist
     */
         @PutMapping("/{id}/process")
        public ResponseEntity<String> processProject(@PathVariable int id){
        List<Task> tasks = service.getProjectTaskById(id);
        if (tasks != null && !tasks.isEmpty()) {
            taskProcessor.processAllTasks(tasks);
            return ResponseEntity.ok("Project tasks are being processed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tasks found for this project.");
        }
    }

    /**
     * Adds a user to a project.
     *
     * @param pId the ID of the project
     * @param uId the ID of the user
     * @return a message indicating the user was added to the project or NOT FOUND if the project/user doesn't exist
     */
    @PostMapping("/{pId}/user/{uId}")
    public ResponseEntity<String> addUserToProjectById(@PathVariable int pId, @PathVariable int uId) {
        service.assignUserToProject(pId, uId);

        return ResponseEntity.ok("User added to the project successfully.");

    }

    /**
     * Retrieves tasks associated with a project.
     *
     * @param pId the ID of the project
     * @return a list of tasks associated with the project or NOT FOUND if the project doesn't exist
     */
    @GetMapping("/{pId}/tasks")
    public ResponseEntity<?> getProjectTasks(@PathVariable int pId) {
        List<Task> tasks = service.getProjectTaskById(pId);
        if (tasks != null && !tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tasks found for this project.");
        }
    }

    /**
     * Retrieves task counts by status for a project.
     *
     * @param pId the ID of the project
     * @return a map of task counts by status or NOT FOUND if the project doesn't exist
     */
    @GetMapping("/{pId}/tasks/status")
    public ResponseEntity<?> getTasksCountByStatus(@PathVariable int pId) {
        Map<Status, Long> taskCounts = service.countTasksStatus(pId);
        if (taskCounts != null && !taskCounts.isEmpty()) {
            return ResponseEntity.ok(taskCounts);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tasks found for this project.");
        }
    }
}
