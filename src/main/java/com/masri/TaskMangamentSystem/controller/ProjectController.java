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
 *  REST controller class for managing {@link Project} entities.
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskProcessor taskProcessor;

    /**
     *  Constructs a ProjectController with the injected ProjectService and TaskProcessor.
     *
     * @param projectService the service layer for managing projects
     * @param taskProcessor for processing project tasks
     */
    @Autowired
    public ProjectController(ProjectService projectService, TaskProcessor taskProcessor) {
        this.projectService = projectService;
        this.taskProcessor = taskProcessor;
    }

    /**
     * Retrieves all projects.
     *
     * @return a list of all projects
     */
    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    /**
     * Creates a new project.
     *
     * @param project the project to be created
     * @return the created project with a CREATED status code
     */
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        projectService.addProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param id the ID of the project to be deleted
     * @return a success message on deletion or a NOT FOUND status code if the project doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted successfully.");
    }

    /**
     * Updates an existing project.
     *
     * @param id the ID of the project to be updated
     * @param project the updated project details
     * @return the updated project or a NOT FOUND status code if the project doesn't exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable int id, @RequestBody Project project) {
        projectService.updateProject(project);
        return ResponseEntity.ok(project);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id the ID of the project to retrieve
     * @return the project with the specified ID or a NOT FOUND status code if the project doesn't exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable int id) {
        Project project = projectService.getById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * Retrieves users associated with a project.
     *
     * @param pId the ID of the project
     * @return a list of users associated with the project or a NOT FOUND status code if the project doesn't exist
     */
    @GetMapping("/{pId}/users")
    public ResponseEntity<?> getProjectUsers(@PathVariable int pId) {
        List<User> users = projectService.getProjectUserById(pId);
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
     * @return a message indicating the project tasks are being processed or a NOT FOUND status code if the project doesn't exist
     */
    @PutMapping("/{id}/process")
    public ResponseEntity<String> processProject(@PathVariable int id) {
        List<Task> tasks = projectService.getProjectTaskById(id);
        if (tasks != null && !tasks.isEmpty()) {
            taskProcessor.processAllTasks(tasks);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Project tasks are being processed.");
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
        projectService.assignUserToProject(pId, uId);
        return ResponseEntity.ok("User added to the project successfully.");
    }

    /**
     * Removes a user from a project.
     *
     * @param pId the ID of the project
     * @param uId the ID of the user
     * @return a NO_CONTENT status code if successful, otherwise a NOT FOUND status code
     */
    @DeleteMapping("/{pId}/user/{uId}")
    public ResponseEntity<String> deleteUserById(@PathVariable int pId, @PathVariable int uId) {
        projectService.removeUserFromProject(pId, uId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("deleted");
    }

    /**
     * Retrieves tasks associated with a project.
     *
     * @param pId the ID of the project
     * @return a list of tasks associated with the project or a NOT FOUND status code if the project doesn't exist
     */
    @GetMapping("/{pId}/tasks")
    public ResponseEntity<?> getProjectTasks(@PathVariable int pId) {
        List<Task> tasks = projectService.getProjectTaskById(pId);
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
     * @return a map of task counts by status or a NOT FOUND status code if the project doesn't exist
     */
    @GetMapping("/{pId}/tasks/status")
    public ResponseEntity<?> getTasksCountByStatus(@PathVariable int pId) {
        Map<Status, Long> taskCounts = projectService.countTasksStatus(pId);
        if (taskCounts != null && !taskCounts.isEmpty()) {
            return ResponseEntity.ok(taskCounts);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tasks found for this project.");
        }
    }
}
