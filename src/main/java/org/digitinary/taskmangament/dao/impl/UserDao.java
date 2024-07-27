package org.digitinary.taskmangament.dao.impl;

import org.digitinary.taskmangament.dao.CrudDao;
import org.digitinary.taskmangament.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for managing {@link User} entities.
 */
@Repository
public class UserDao implements CrudDao<User> {

    private final EntityManager entityManager;

    /**
     * Constructs a UserDao with the specified EntityManager.
     *
     * @param entityManager the EntityManager for database operations.
     */
    @Autowired
    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param user The entity to be added.
     */
    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    /**
     *
     * @param user The entity with updated information.
     */
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    /**
     *
     * @param id The ID of the entity to find.
     * @return the user if exist
     */
    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    /**
     *
     * @param user The entity to be deleted.
     */
    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    /**
     *
     * @return all users
     */
    @Override
    public List<User> getAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }


    /**
     * Checks if a user with the specified email exists.
     *
     * @param email the email address to check.
     * @return true if a user with the email exists, false otherwise.
     */
    public boolean checkEmailUniqueness(String email) {
        try {
            entityManager.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
