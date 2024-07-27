package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.dao.impl.ProjectDao;
import com.masri.TaskMangamentSystem.dao.impl.UserDao;
import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.excptions.exception.ProjectNotFoundExecption;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectUserService {
    private final ProjectDao projectDao;

    @Autowired
    public ProjectUserService(ProjectDao projectDao, UserDao userDao) {
        this.projectDao = projectDao;
    }

    /**
     * Removes a user from all projects they are associated with.
     *
     * @param user the user to be removed from projects
     * @throws ProjectNotFoundExecption if a project is not found
     */
    @Transactional
    public void removeUserFromProjects(User user) throws ProjectNotFoundExecption {
        List<Project> projects = projectDao.findByUser(user);
        for (Project project : projects) {
            project.getUsers().remove(user);
            projectDao.update(project);
        }
    }
}
