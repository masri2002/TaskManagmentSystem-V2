package org.digitinary.taskmangament.dao.impl;

import org.digitinary.taskmangament.dao.CrudDao;
import org.digitinary.taskmangament.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for managing {@link Task} entities.
 */
@Repository
public class TaskDao implements CrudDao<Task> {

    private final EntityManager entityManager;

    /**
     * Constructs a TaskDao with the specified EntityManager.
     *
     * @param entityManager the EntityManager to be used for database operations
     */
    @Autowired
    public TaskDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param task The entity to be added.
     */
    @Override
    public void add(Task task) {
        entityManager.persist(task);
    }

    /**
     *
     * @param task The entity with updated information.
     */
    @Override
    public void update(Task task) {
        entityManager.merge(task);
    }

    /**
     *
     * @param id The ID of the entity to find.
     * @return the task if exist
     */
    @Override
    public Task findById(int id) {
        return entityManager.find(Task.class, id);
    }

    /**
     *
     * @param task The entity to be deleted.
     */
    @Override
    public void delete(Task task) {
        Task foundTask = findById(task.getId());
        if (foundTask != null) {
            entityManager.remove(foundTask);
        }
    }

    /**
     *
     * @return all tasks
     */
    @Override
    public List<Task> getAll() {
        return entityManager.createQuery("FROM Task", Task.class).getResultList();
    }


    /**
     * Checks if a task with the specified title exists.
     *
     * @param title the title to check
     * @return true if a task with the title exists, false otherwise
     */
    public boolean existTaskByTitle(String title) {
        try {
            entityManager.createQuery("FROM Task WHERE title = :title", Task.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Retrieves a task with its associated project by task ID.
     *
     * @param taskId the task ID
     * @return the task with its associated project
     */
    public Task getTaskProjectById(int taskId) {
        return entityManager.createQuery("SELECT t FROM Task t JOIN FETCH t.project WHERE t.id = :taskId", Task.class)
                .setParameter("taskId", taskId)
                .getSingleResult();
    }


}
