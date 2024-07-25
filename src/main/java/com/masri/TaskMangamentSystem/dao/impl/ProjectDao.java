package com.masri.TaskMangamentSystem.dao.impl;
import com.masri.TaskMangamentSystem.dao.CrudDao;
import com.masri.TaskMangamentSystem.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
@Transactional
public class ProjectDao implements CrudDao<Project>{
    private final EntityManager em;

    @Autowired
    public ProjectDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public void add(Project project) {
        em.persist(project);
    }

    @Override
    public void update(Project project) {
      em.merge(project);
    }

    @Override
    public Project findById(int id) {
        return em.find(Project.class,id);
    }

    @Override
    public void delete(Project project) {
      em.remove(project);
    }

    @Override
    public List<Project> getAll() {
        return em.createQuery("from Project ",Project.class).getResultList();
    }

    public boolean checkExistsByTitle(String title){
        Project project;
        try {
            project=em.createQuery("from Project where title=:data",Project.class).
                    setParameter("data",title).getSingleResult();
        }catch (NoResultException ex){
            project=null;
        }
        return project==null ?false : true;
    }
    public Project getProjectWithItsTaskAndUsersById(int id){
        return em.createQuery("SELECT p FROM Project p " +
                                    " JOIN FETCH p.tasks " +
                                        "JOIN FETCH p.users " +
                                           "WHERE p.id = :data" ,Project.class)
                      .setParameter("data",id).getSingleResult();
    }

    public void add(){

    }
}
