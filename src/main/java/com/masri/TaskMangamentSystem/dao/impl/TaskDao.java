package com.masri.TaskMangamentSystem.dao.impl;

import com.masri.TaskMangamentSystem.dao.CrudDao;
import com.masri.TaskMangamentSystem.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link CrudDao} interface for managing {@link Task} entities.
 * <p>
 * This class provides methods for basic CRUD operations and additional queries specific
 * to the {@link Task} entity.
 * </p>
 * @author ahmdd almasri
 */
@Repository
public class TaskDao implements CrudDao<Task> {

    private final EntityManager em;

    /**
     * Constructor for the TaskDao class.
     * <p>
     * Initializes the EntityManager for interacting with the database.
     * </p>
     *
     * @param em The EntityManager to be used by this DAO.
     */
    @Autowired
    public TaskDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Adds a new {@link Task} entity to the database.
     *
     * @param task The task entity to be added.
     */
    @Override
    public void add(Task task) {
        em.persist(task);
    }

    /**
     * Updates an existing {@link Task} entity in the database.
     *
     * @param task The task entity to be updated.
     */
    @Override
    public void update(Task task) {
        em.merge(task);
    }

    /**
     * Finds a {@link Task} entity by its unique identifier.
     *
     * @param id The unique identifier of the task.
     * @return The task entity with the specified ID, or null if not found.
     */
    @Override
    public Task findById(int id) {
        return em.find(Task.class, id);
    }

    /**
     * Deletes a {@link Task} entity from the database.
     * <p>
     * The task to be deleted is first retrieved using its ID. If the task exists,
     * it is removed from the database.
     * </p>
     *
     * @param task The task entity to be deleted.
     */
    @Override
    public void delete(Task task) {
        Task foundTask = findById(task.getId());
        if (foundTask != null) {
            em.remove(foundTask);
        }
    }

    /**
     * Retrieves a list of all {@link Task} entities from the database.
     *
     * @return A list of all task entities.
     */
    @Override
    public List<Task> getAll() {
        return em.createQuery("FROM Task", Task.class).getResultList();
    }

    @Override
    public void deleteAll() {
       em.createQuery("DELETE FROM Task").executeUpdate();
    }

    /**
     * Checks if a task with the specified title exists in the database.
     *
     * @param title The title of the task to check.
     * @return True if a task with the specified title exists, false otherwise.
     */
    public boolean existTaskByTitle(String title) {
        try {
            em.createQuery("FROM Task WHERE title = :title", Task.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
    public Task getTaskProjectById(int taskId){
        return em.createQuery("select t from Task t "+
                "join fetch t.project "+"where t.id=:data",Task.class).setParameter("data",taskId)
                .getSingleResult();
    }
}
