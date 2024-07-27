package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.dao.impl.ProjectDao;
import com.masri.TaskMangamentSystem.entity.*;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateProjectsException;
import com.masri.TaskMangamentSystem.excptions.exception.ProjectNotContainsTaskException;
import com.masri.TaskMangamentSystem.excptions.exception.ProjectNotFoundExecption;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing projects and their associated tasks.
 * Provides methods for adding, retrieving, updating, and deleting projects.
 * Author: Ahmad Almasri
 */
@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final UserService userService;

    /**
     * Constructs a ProjectService with the specified ProjectDao and UserService.
     *
     * @param projectDao the DAO used to interact with project data
     * @param userService the service used to interact with user data
     */
    @Autowired
    public ProjectService(ProjectDao projectDao, UserService userService) {
        this.projectDao = projectDao;
        this.userService = userService;
    }

    /**
     * Adds a new project if it does not already exist.
     *
     * @param project the project to be added
     * @throws DuplicateProjectsException if a project with the same name already exists
     */
    @Transactional
    public void addProject(Project project) {
        if (projectDao.checkExistsByTitle(project.getName())) {
            throw new DuplicateProjectsException("Project Already Exists");
        } else {
            projectDao.add(project);
        }
    }

    /**
     * Retrieves a project by its unique identifier.
     *
     * @param id the unique identifier of the project
     * @return the project with the specified ID
     * @throws ProjectNotFoundExecption if no project is found with the given ID
     */
    public Project getById(int id) {
        Project project = projectDao.findById(id);
        if (project == null) {
            throw new ProjectNotFoundExecption("Project Not Found");
        }
        return project;
    }

    /**
     * Updates an existing project.
     *
     * @param project the project with updated information
     * @throws ProjectNotFoundExecption if the project to be updated does not exist
     * @throws ProjectNotFoundExecption if a project with the new title already exists
     */
    @Transactional
    public void updateProject(Project project) throws ProjectNotFoundExecption {
        Project oldProject = getById(project.getId());

        if (project.getName() != null && !project.getName().equals(oldProject.getName())) {
            if (projectDao.checkExistsByTitle(project.getName())) {
                throw new DuplicateProjectsException("Project With This Title Already Exists");
            } else {
                oldProject.setName(project.getName());
            }
        }
        if (project.getDescription() != null) {
            oldProject.setDescription(project.getDescription());
        }

        projectDao.update(oldProject);
    }

    /**
     * Deletes a project by its unique identifier.
     *
     * @param id the unique identifier of the project to be deleted
     * @throws ProjectNotFoundExecption if no project is found with the given ID
     */
    @Transactional
    public void deleteProject(int id) throws ProjectNotFoundExecption {
        Project project = getById(id);
        projectDao.delete(project);
    }

    /**
     * Retrieves a list of users associated with a project.
     *
     * @param projectId the unique identifier of the project
     * @return a list of users associated with the project
     */
    public List<User> getProjectUserById(int projectId) {
        Project project = getById(projectId);
        return project.getUsers().stream().toList();
    }

    /**
     * Retrieves a list of tasks associated with a project.
     *
     * @param projectId the unique identifier of the project
     * @return a list of tasks associated with the project
     */
    public List<Task> getProjectTaskById(int projectId) {
        Project project = getById(projectId);
        return project.getTasks().stream().toList();
    }

    /**
     * Assigns a user to a project.
     *
     * @param projectId the unique identifier of the project
     * @param userId the unique identifier of the user
     */
    @Transactional
    public void assignUserToProject(int projectId, int userId) {
        Project project = getById(projectId);
        User user = userService.getUserById(userId);
        project.addUser(user);
        updateProject(project);
    }

    /**
     * Counts the number of tasks in a project by their status.
     *
     * @param projectId the unique identifier of the project
     * @return a map of task statuses and their counts
     * @throws ProjectNotFoundExecption if the project is not found
     * @throws ProjectNotContainsTaskException if the project does not contain tasks
     */
    public Map<Status, Long> countTasksStatus(int projectId) throws ProjectNotFoundExecption, ProjectNotContainsTaskException {
        Project project = getById(projectId);
        if (project.getTasks().isEmpty()) {
            throw new ProjectNotContainsTaskException("Project does not have tasks");
        }
        return project.getTasks().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }

    /**
     * Counts the number of tasks in a project by a specific status.
     *
     * @param projectId the unique identifier of the project
     * @param status the status to count tasks by
     * @return the number of tasks with the specified status
     * @throws ProjectNotFoundExecption if the project is not found
     * @throws ProjectNotContainsTaskException if the project does not contain tasks
     */
    public long countTasksByStatus(int projectId, Status status) throws ProjectNotFoundExecption, ProjectNotContainsTaskException {
        Project project = getById(projectId);
        if (project.getTasks().isEmpty()) {
            throw new ProjectNotContainsTaskException("Project does not have tasks");
        }
        return project.getTasks().stream()
                .filter(task -> task.getStatus() == status)
                .count();
    }

    /**
     * Retrieves all projects.
     *
     * @return a list of all projects
     */
    public List<Project> getAllProjects() {
        return projectDao.getAll();
    }

    /**
     * Removes a user from a project.
     *
     * @param projectId the unique identifier of the project
     * @param userId the unique identifier of the user
     */
    @Transactional
    public void removeUserFromProject(int projectId, int userId) {
        Project project = getById(projectId);
        User user = userService.getUserById(userId);
        project.getUsers().remove(user);
        updateProject(project);
    }
}
