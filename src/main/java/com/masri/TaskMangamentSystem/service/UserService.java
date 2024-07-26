package com.masri.TaskMangamentSystem.service;

import com.masri.TaskMangamentSystem.dao.impl.UserDao;
import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateUserExecption;
import com.masri.TaskMangamentSystem.excptions.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class that manages users in the Task Management System.
 * Provides functionality for adding, retrieving, updating, and deleting users.
 * @author ahmad almasri
 */
@Service
@Transactional
public class UserService {

    private final UserDao userDao;

    /**
     * Constructs a UserService with the specified UserDao.
     *
     * @param userDao the DAO used to interact with user data
     */
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Adds a new user if the email is not already in use.
     *
     * @param user the user to be added
     * @throws DuplicateUserExecption if the email is already used by another user
     */
    public void addUser(User user) throws DuplicateUserExecption {
        if (userDao.checkEmailUniqueness(user.getEmail())) {
            throw new DuplicateUserExecption("User Email Already Used");
        } else {
            userDao.add(user);
        }
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the user with the specified ID
     * @throws UserNotFoundException if no user is found with the given ID
     */
    public User getUserById(int id) throws UserNotFoundException {
        User user = userDao.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        return user;
    }

    /**
     * Updates an existing user's information.
     *
     * @param user the user with updated information
     * @throws UserNotFoundException if the user to be updated does not exist
     * @throws DuplicateUserExecption if the updated email is already used by another user
     */
    public void updateUser(User user) throws UserNotFoundException, DuplicateUserExecption {
        User oldUser = getUserById(user.getId());

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            if (!user.getEmail().equals(oldUser.getEmail())) {
                if (userDao.checkEmailUniqueness(user.getEmail())) {
                    throw new DuplicateUserExecption("User Email Already Used");
                }
                oldUser.setEmail(user.getEmail());
            }
        }

        userDao.update(oldUser);
    }

    /**
     * Deletes a user by their unique identifier.
     * Also removes the user from all projects they are associated with.
     *
     * @param id the unique identifier of the user to be deleted
     * @throws UserNotFoundException if no user is found with the given ID
     */
    public void deleteUserById(int id) throws UserNotFoundException {
        User user = getUserById(id);
        // Remove user from all projects
        if(user.getProjects()!=null)
            for (Project project : user.getProjects()) {
                project.getUsers().remove(user);
            }
        userDao.delete(user);
    }
}