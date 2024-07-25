package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.dao.impl.ProjectDao;
import com.masri.TaskMangamentSystem.entity.*;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateProjectsException;
import com.masri.TaskMangamentSystem.excptions.exception.ProjectNotContainsTaskException;
import com.masri.TaskMangamentSystem.excptions.exception.ProjectNotFoundExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing projects and their associated tasks.
 */
@Service
public class ProjectService {

    private final ProjectDao dao;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    public ProjectService(ProjectDao dao, UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    /**
     * Retrieves all projects.
     * @return A list of all projects.
     */
    public List<Project> getAll() {
        return dao.getAll();
    }

    /**
     * Adds a new project to the database.
     * @param project The project to be added.
     * @return true if the project was added successfully; false if it already exists.
     * @throws DuplicateProjectsException If the project already exists.
     */
    public boolean add(Project project) throws DuplicateProjectsException {
        if (!dao.checkExistsByTitle(project.getTitle())) {
            dao.add(project);
            logger.info("Project added: {}", project);
            return true;
        } else {
            throw new DuplicateProjectsException("Project already exists");
        }
    }

    /**
     * Retrieves a project by its ID.
     * @param id The ID of the project to be retrieved.
     * @return The project with the specified ID.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public Project getById(int id) throws ProjectNotFoundExecption {
        Project project = dao.findById(id);
        if (project == null) {
            throw new ProjectNotFoundExecption("Project does not exist");
        }
        return project;
    }

    /**
     * Deletes a project by its ID.
     * @param id The ID of the project to be deleted.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public void deleteById(int id) throws ProjectNotFoundExecption {
        Project project = getById(id);
        dao.delete(project);
    }

    /**
     * Adds a task to a project by project ID.
     * @param projectId The ID of the project.
     * @param task The task to be added.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public void addTaskToProjectById(int projectId, Task task) throws ProjectNotFoundExecption {
        Project project = getById(projectId);
        project.addTask(task);
        dao.update(project);
    }

    /**
     * Updates the priority of a task in a project by project ID and task ID.
     * @param projectId The ID of the project.
     * @param taskId The ID of the task.
     * @param priority The new priority of the task.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     * @throws ProjectNotContainsTaskException If the project does not contain the task with the given ID.
     */
    public void updateTaskPriority(int projectId, int taskId, Priority priority) throws ProjectNotFoundExecption, ProjectNotContainsTaskException {
        Project project = getById(projectId);
        Task task = project.getTasks().stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElseThrow(() -> new ProjectNotContainsTaskException("Task does not exist in the project"));
        task.setPriority(priority);
        dao.update(project);
    }

    /**
     * Counts the tasks by their status in a project.
     * @param projectId The ID of the project.
     * @param status The status of the tasks to be counted.
     * @return The count of tasks with the specified status.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     * @throws ProjectNotContainsTaskException If the project does not contain any tasks.
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
     * Counts the tasks by their status in a project.
     * @param projectId The ID of the project.
     * @return A map of status to the count of tasks with that status.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     * @throws ProjectNotContainsTaskException If the project does not contain any tasks.
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
     * Adds a user to a project by project ID.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to be added.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public void addUserToProjectById(int projectId, int userId) throws ProjectNotFoundExecption {
        Project project = getById(projectId);
        User user = userService.getById(userId);
        project.addUser(user);

        if (user.getTasks() != null) {
            for (Task task : user.getTasks()) {
                if (task.getProject() == null) {
                    project.addTask(task);
                    task.setProject(project);
                }
            }
        }
        dao.update(project);
    }

    /**
     * Retrieves all users associated with a project by its ID.
     * @param id The ID of the project.
     * @return A list of users associated with the project.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public List<User> getProjectUsersById(int id) throws ProjectNotFoundExecption {
        Project project = dao.getProjectWithItsTaskAndUsersById(id);
        if (project == null) {
            throw new ProjectNotFoundExecption("Project does not exist");
        }
        return new ArrayList<>(project.getUsers());
    }

    /**
     * Retrieves all tasks associated with a project by its ID.
     * @param id The ID of the project.
     * @return A list of tasks associated with the project.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public List<Task> getProjectTasksById(int id) throws ProjectNotFoundExecption {
        Project project = dao.getProjectWithItsTaskAndUsersById(id);
        if (project == null) {
            throw new ProjectNotFoundExecption("Project does not exist");
        }
        return new ArrayList<>(project.getTasks());
    }

    /**
     * Removes a user from a project by project ID and user ID.
     * @param projectId The ID of the project.
     * @param userId The ID of the user to be removed.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public void removeUserFromProjectById(int projectId, int userId) throws ProjectNotFoundExecption {
        Project project = dao.getProjectWithItsTaskAndUsersById(projectId);
        User user = project.getUsers().stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElseThrow(() -> new ProjectNotFoundExecption("User not found in the project"));

        user.getProjects().remove(project);
        project.getTasks().removeIf(task -> {
            if (task.getProject() != null && task.getProject().getId() == project.getId()) {
                task.setProject(null);
                return true;
            }
            return false;
        });

        dao.update(project);
    }

    /**
     * Removes a task from a project by project ID and task ID.
     * @param projectId The ID of the project.
     * @param taskId The ID of the task to be removed.
     * @throws ProjectNotFoundExecption If the project with the given ID does not exist.
     */
    public void removeTaskFromProjectById(int projectId, int taskId) throws ProjectNotFoundExecption {
        Project project = dao.getProjectWithItsTaskAndUsersById(projectId);
        Task task = project.getTasks().stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElseThrow(() -> new ProjectNotFoundExecption("Task not found in the project"));

        project.getTasks().remove(task);
        task.setProject(null);
        dao.update(project);
    }
}
