package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.dao.impl.TaskDao;
import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.Status;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class that manages tasks in the Task Management System.
 * Provides functionality for adding, retrieving, updating, and deleting tasks,
 * as well as assigning tasks to projects.
 * Author: Ahmad Almasri
 */
@Service
public class TaskService {

    private final TaskDao taskDao;
    private final ProjectService projectService;

    /**
     * Constructs a TaskService with the specified TaskDao and ProjectService.
     *
     * @param taskDao        the DAO used to interact with task data
     * @param projectService the service used to interact with project data
     */
    @Autowired
    public TaskService(TaskDao taskDao, ProjectService projectService) {
        this.taskDao = taskDao;
        this.projectService = projectService;
    }

    /**
     * Adds a new task if it does not already exist.
     *
     * @param task the task to be added
     * @throws DuplicateTaskExecption if a task with the same title already exists
     */
    @Transactional
    public void addTask(Task task) {
        if (taskDao.existTaskByTitle(task.getTitle())) {
            throw new DuplicateTaskExecption("Task already exists");
        } else {
            task.setCreationDate(LocalDate.now());
            taskDao.add(task);
        }
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id the unique identifier of the task
     * @return the task with the specified ID
     * @throws TaskNotExistExecption if no task is found with the given ID
     */
    public Task getById(int id) {
        Task task = taskDao.findById(id);
        if (task == null) {
            throw new TaskNotExistExecption("Task does not exist");
        }
        return task;
    }

    /**
     * Deletes a task by its unique identifier.
     *
     * @param taskId the unique identifier of the task to be deleted
     * @throws TaskNotExistExecption if no task is found with the given ID
     */
    @Transactional
    public void deleteTask(int taskId) {
        Task task = getById(taskId);
        taskDao.delete(task);
    }

    /**
     * Updates an existing task with new information.
     *
     * @param task the task with updated information
     * @throws TaskNotExistExecption if the task to be updated does not exist
     * @throws DuplicateTaskExecption if a task with the new title already exists
     */
    @Transactional
    public void updateTask(Task task) throws TaskNotExistExecption {
        Task oldTask = getById(task.getId());

        if (!oldTask.getTitle().equals(task.getTitle())) {
            if (taskDao.existTaskByTitle(task.getTitle())) {
                throw new DuplicateTaskExecption("Task title already exists");
            } else {
                oldTask.setTitle(task.getTitle());
            }
        }

        if (task.getDescription() != null) {
            oldTask.setDescription(task.getDescription());
        }
        if (task.getDueDate() != null) {
            oldTask.setDueDate(task.getDueDate());
        }
        if (task.getStatus() != null) {
            oldTask.setStatus(task.getStatus());
        }
        if (task.getPriority() != null) {
            oldTask.setPriority(task.getPriority());
        }
        if (task.getProject() != null) {
            oldTask.setProject(task.getProject());
        }
        if (task.getCreationDate() != null) {
            oldTask.setCreationDate(task.getCreationDate());
        }

        taskDao.update(oldTask);
    }

    /**
     * Assigns a task to a project.
     *
     * @param taskId    the unique identifier of the task
     * @param projectId the unique identifier of the project
     */
    @Transactional
    public void assignTaskToProject(int taskId, int projectId) {
        Project project = projectService.getById(projectId);
        Task task = getById(taskId);
        task.setProject(project);
        project.addTask(task);
        projectService.updateProject(project);
    }

    /**
     * Retrieves the project associated with a given task.
     *
     * @param taskId the unique identifier of the task
     * @return the project associated with the task
     */
    public Project getProjectById(int taskId) {
        return taskDao.findById(taskId).getProject();
    }

    /**
     * Counts the tasks grouped by their status.
     *
     * @return a map of task statuses and their counts
     */
    public Map<Status, Long> tasksCountGroupingByStatus() {
        return taskDao.getAll().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }
}
