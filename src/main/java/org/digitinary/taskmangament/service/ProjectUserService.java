package org.digitinary.taskmangament.service;

import org.digitinary.taskmangament.dao.impl.ProjectDao;
import org.digitinary.taskmangament.dao.impl.UserDao;
import org.digitinary.taskmangament.entity.Project;
import org.digitinary.taskmangament.entity.User;
import org.digitinary.taskmangament.excptions.exception.ProjectNotFoundExecption;
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

    public List<Project> userProjects (User user){
         return projectDao.findByUser(user);
    }
}
