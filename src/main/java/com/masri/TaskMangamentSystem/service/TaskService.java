package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.dao.impl.TaskDao;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
import com.masri.TaskMangamentSystem.entity.Priority;
import com.masri.TaskMangamentSystem.entity.Status;
import com.masri.TaskMangamentSystem.entity.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class that manages tasks in the Task Management System.
 */
@Service
public class TaskService {

    private final TaskDao dao;
    private final Logger logger;

    @Autowired
    public TaskService(TaskDao dao) {
        this.dao = dao;
        this.logger = LoggerFactory.getLogger(TaskService.class);
    }

    /**
     * Adds a task to the database.
     *
     * @param task The task to add.
     * @return true if the task was successfully added
     * @throws DuplicateTaskExecption If the task already exists.
     */
    public boolean add(Task task) {
        if (dao.existTaskByTitle(task.getTitle())) {
            throw new DuplicateTaskExecption("Task Already Exists");
        } else {
            dao.add(task);
            logger.info("Task added: {}", task);
            return true;
        }
    }

    /**
     * Updates an existing task by its ID.
     *
     * @param task The updated task object.
     * @throws TaskNotExistExecption If the task with the given ID does not exist.
     */
    public void update(Task task) throws TaskNotExistExecption {
        Task existingTask = getById(task.getId());
        if (task.getPriority() != null)
            existingTask.setPriority(task.getPriority());
        if (task.getDate() != null)
            existingTask.setDate(task.getDate());
        if (task.getStatus() != null)
            existingTask.setStatus(task.getStatus());
        if (task.getDueDate() != null)
            existingTask.setDueDate(task.getDueDate());
        if (task.getDescription() != null)
            existingTask.setDescription(task.getDescription());
        if (task.getTitle() != null)
            existingTask.setTitle(task.getTitle());

        dao.update(existingTask);
        logger.info("Task updated: {}", existingTask);
    }

    /**
     * Updates the status of a task by its ID.
     *
     * @param id     The ID of the task to update.
     * @param status The new status of the task.
     * @throws TaskNotExistExecption If the task with the given ID does not exist.
     */
    public void updateTaskStatus(int id, Status status) throws TaskNotExistExecption {
        Task task = getById(id);
        task.setStatus(status);
        dao.update(task);
        logger.info("Task status updated: {} to {}", task.getId(), status);
    }

    /**
     * Updates the priority of a task by its ID.
     *
     * @param id       The ID of the task to update.
     * @param priority The new priority of the task.
     * @throws TaskNotExistExecption If the task with the given ID does not exist.
     */
    public void updatePriority(int id, Priority priority) throws TaskNotExistExecption {
        Task task = getById(id);
        task.setPriority(priority);
        dao.update(task);
        logger.info("Task priority updated: {} to {}", task.getId(), priority);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete.
     * @throws TaskNotExistExecption If the task with the given ID does not exist.
     */
    public void deleteById(int id) throws TaskNotExistExecption {
        Task task = getById(id);
        dao.delete(task);
        logger.info("Task deleted: {}", task);
    }

    /**
     * Retrieves all tasks.
     *
     * @return A list of all tasks.
     */
    public List<Task> getAll() {
        return dao.getAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to retrieve.
     * @return The task object.
     * @throws TaskNotExistExecption If the task with the given ID does not exist.
     */
    public Task getById(int id) throws TaskNotExistExecption {
        Task task = dao.findById(id);
        if (task == null) {
            throw new TaskNotExistExecption("Task with ID " + id + " does not exist.");
        }
        return task;
    }

    /**
     * Counts tasks by their status.
     *
     * @param status The status to count tasks for.
     * @return The number of tasks with the specified status.
     */
    public long countTasksByStatus(Status status) {
        return dao.getAll().stream()
                .filter(task -> task.getStatus().equals(status))
                .count();
    }

    /**
     * Counts tasks grouped by their status.
     *
     * @return A map where keys are task statuses and values are counts of tasks with each status.
     */
    public Map<Status, Long> TasksCountGroupingByStatus() {
        return dao.getAll().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }
}
