package com.masri.TaskMangamentSystem.dao.impl;

import com.masri.TaskMangamentSystem.dao.CrudDao;
import com.masri.TaskMangamentSystem.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TaskDao implements CrudDao<Task> {
    private final EntityManager em;

    @Autowired
    public TaskDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public void add(Task task) {
        em.persist(task);
    }

    @Override
    public void update(Task task) {
        em.merge(task);
    }

    @Override
    public Task findById(int id) {
        return em.find(Task.class, id);
    }

    @Override
    public void delete(Task task) {
        Task foundTask = findById(task.getId());
        if (foundTask != null) {
            em.remove(foundTask);
        }
    }

    @Override
    public List<Task> getAll() {
        return em.createQuery("FROM Task ", Task.class).getResultList();
    }

    public boolean existTaskByTitle(String title) {
        try {
            Task task = em.createQuery("FROM Task where title = :title", Task.class)
                    .setParameter("title", title)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
