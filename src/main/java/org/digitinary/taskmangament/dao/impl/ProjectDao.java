package org.digitinary.taskmangament.dao.impl;

import org.digitinary.taskmangament.dao.CrudDao;
import org.digitinary.taskmangament.entity.Project;
import org.digitinary.taskmangament.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for managing {@link Project} entities.
 */
@Repository
public class ProjectDao implements CrudDao<Project> {

    private final EntityManager entityManager;

    /**
     * Constructs a ProjectDao with the specified EntityManager.
     *
     * @param entityManager the EntityManager to be used for database operations
     */
    @Autowired
    public ProjectDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param project The entity to be added.
     */
    @Override
    public void add(Project project) {
        entityManager.persist(project);
    }

    /**
     *
     * @param project The entity with updated information.
     */
    @Override
    public void update(Project project) {
        entityManager.merge(project);
    }

    /**
     *
     * @param id The ID of the entity to find.
     * @return the project if exist
     */
    @Override
    public Project findById(int id) {
        return entityManager.find(Project.class, id);
    }

    /**
     *
     * @param project The entity to be deleted.
     */
    @Override
    public void delete(Project project) {
        entityManager.remove(entityManager.contains(project) ? project : entityManager.merge(project));
    }

    /**
     *
     * @return list of All project
     */
    @Override
    public List<Project> getAll() {
        return entityManager.createQuery("FROM Project", Project.class).getResultList();
    }


    /**
     * Checks if a project with the specified title exists.
     *
     * @param title the title to check
     * @return true if a project with the title exists, false otherwise
     */
    public boolean checkExistsByTitle(String title) {
        try {
            entityManager.createQuery("FROM Project WHERE name = :title", Project.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Finds projects by a user.
     *
     * @param user the user to find projects for
     * @return a list of projects associated with the user
     */
    public List<Project> findByUser(User user) {
        return entityManager.createQuery("SELECT p FROM Project p JOIN" +
                        " p.users u WHERE u = :user", Project.class)
                .setParameter("user", user)
                .getResultList();
    }

    /**
     * retrieves eagerly project details
     *
     * @param id for project want to return
     * @return project details
     */
    public Project findProjectDetails(int id){
       return entityManager.createQuery("select p from Project p "
        +"join fetch p.tasks "
        + "join fetch p.users "
                +"where p.id=:id",Project.class).setParameter("id",id).getSingleResult();
    }
}
