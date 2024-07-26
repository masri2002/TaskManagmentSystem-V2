package com.masri.TaskMangamentSystem.dao.impl;

import com.masri.TaskMangamentSystem.dao.CrudDao;
import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO (Data Access Object) class for managing User entities.
 * Provides CRUD operations and additional methods for user-specific queries.
 * @author ahmad almasri
 */
@Repository
public class UserDao implements CrudDao<User> {
    private final EntityManager entityManager;
    /**
     * Constructs a UserDao with the specified EntityManager.
     *
     * @param entityManager the EntityManager to be used for persistence operations.
     */
    @Autowired
    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    /**
     * Adds a new User entity to the database.
     *
     * @param user the User entity to be added.
     */
    @Override
    public void add(User user) {
        entityManager.persist(user);
    }
    /**
     * Updates an existing User entity in the database.
     *
     * @param user the User entity with updated information.
     */
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
    /**
     * Checks if a user with the specified email address already exists in the database.
     *
     * @param email the email address to check for uniqueness.
     * @return true if the email is already used by another user, false otherwise.
     */
    public boolean checkEmailUniqueness(String email) {
        User existUser = null;
        try {
            existUser = entityManager.createQuery("FROM User where email=:data", User.class)
                    .setParameter("data", email)
                    .getSingleResult();
        } catch (NoResultException e) {
        }
        return existUser != null;
    }

    /**
     * Finds a User entity by its ID.
     *
     * @param id the ID of the User entity to find.
     * @return the User entity with the specified ID, or null if not found.
     */
    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Deletes a User entity from the database.
     *
     * @param user the User entity to be deleted.
     */
    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    /**
     * Retrieves a list of all User entities from the database.
     *
     * @return a list of all User entities.
     */
    @Override
    public List<User> getAll() {
        return entityManager.createQuery("FROM User ", User.class).getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from User").executeUpdate();
    }


}
