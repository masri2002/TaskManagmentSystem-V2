package com.masri.TaskMangamentSystem.dao.impl;

import com.masri.TaskMangamentSystem.dao.CrudDao;
import com.masri.TaskMangamentSystem.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link CrudDao} interface for managing {@link Project} entities.
 * <p>
 * This class provides methods for basic CRUD operations and additional queries specific
 * to the {@link Project} entity.
 * </p>
 * @author ahmad almasri
 */
@Repository
public class ProjectDao implements CrudDao<Project> {

    private static final Logger log = LoggerFactory.getLogger(ProjectDao.class);
    private final EntityManager em;

    /**
     * Constructor for the ProjectDao class.
     * <p>
     * Initializes the EntityManager for interacting with the database.
     * </p>
     *
     * @param em The EntityManager to be used by this DAO.
     */
    @Autowired
    public ProjectDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Adds a new {@link Project} entity to the database.
     *
     * @param project The project entity to be added.
     */
    @Override
    public void add(Project project) {
        em.persist(project);
    }

    /**
     * Updates an existing {@link Project} entity in the database.
     *
     * @param project The project entity to be updated.
     */
    @Override
    public void update(Project project) {
        em.merge(project);
    }

    /**
     * Finds a {@link Project} entity by its unique identifier.
     *
     * @param id The unique identifier of the project.
     * @return The project entity with the specified ID, or null if not found.
     */
    @Override
    public Project findById(int id) {
        return em.find(Project.class, id);
    }

    /**
     * Deletes a {@link Project} entity from the database.
     *
     * @param project The project entity to be deleted.
     */
    @Override
    public void delete(Project project) {
        em.remove(project);
    }

    /**
     * Retrieves a list of all {@link Project} entities from the database.
     *
     * @return A list of all project entities.
     */
    @Override
    public List<Project> getAll() {
        return em.createQuery("from Project", Project.class).getResultList();
    }

    @Override
    public void deleteAll() {
        em.createQuery("DELETE FROM Project").executeUpdate();
    }

    /**
     * Checks if a project with the specified title exists in the database.
     *
     * @param title The title of the project to check.
     * @return True if a project with the specified title exists, false otherwise.
     */
    public boolean checkExistsByTitle(String title) {
        Project project;
        try {
            project = em.createQuery("from Project where name=:data", Project.class)
                    .setParameter("data", title)
                    .getSingleResult();
        } catch (NoResultException ex) {
            project = null;
        }
        return project != null;
    }

    /**
     * Retrieves a {@link Project} entity along with its associated tasks and users by its ID.
     * <p>
     * This method uses a join fetch to eagerly load the project's tasks and users.
     * </p>
     *
     * @param id The ID of the project to retrieve.
     * @return The project entity with its associated tasks and users.
     */
    public Project getProjectWithItsTaskAndUsersById(int id) {
        try {
            return em.createQuery("SELECT p FROM Project p " +
                            "JOIN FETCH p.tasks " +
                            "JOIN FETCH p.users " +
                            "WHERE p.id = :data", Project.class)
                    .setParameter("data", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("null");
        }
        return findById(id);
    }

}
