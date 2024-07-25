package com.masri.TaskMangamentSystem.dao;

import java.util.List;

/**
 * A generic interface for CRUD (Create, Read, Update, Delete) operations.
 * This interface provides basic methods to be implemented by DAO classes
 * for managing entities in the data store.
 *
 * @param <T> The type of entity the DAO will manage.
 */
public interface CrudDao<T> {

    /**
     * Adds a new entity to the data store.
     *
     * @param t The entity to be added.
     */
    void add(T t);

    /**
     * Updates an existing entity in the data store.
     *
     * @param t The entity with updated information.
     */
    void update(T t);

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity to find.
     * @return The entity with the specified ID, or null if not found.
     */
    T findById(int id);

    /**
     * Deletes an entity from the data store.
     *
     * @param t The entity to be deleted.
     */
    void delete(T t);

    /**
     * Retrieves a list of all entities in the data store.
     *
     * @return A list of all entities.
     */
    List<T> getAll();
}
