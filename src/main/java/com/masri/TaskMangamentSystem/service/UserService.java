package com.masri.TaskMangamentSystem.service;
import com.masri.TaskMangamentSystem.dao.impl.UserDao;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateUserExecption;
import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
import com.masri.TaskMangamentSystem.excptions.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


/**
 * Service class for managing users.
 * @author Ahmad Al-masri
 */
@Service
public class UserService {

    private final UserDao dao;
    private final Logger logger;
    private final TaskService service;
    @Autowired
    public UserService(UserDao dao , TaskService service) {
        this.dao = dao;
        this.logger = LoggerFactory.getLogger(UserService.class);
        this.service=service;
    }

    /**
     * Adds a new user to the service.
     *
     * @param user The user details to add.
     * @throws DuplicateUserExecption If a user with the same email already exists.
     */
    public void add(User user) throws DuplicateUserExecption {
        if (dao.checkEmailUniqueness(user.getEmail())) {
            throw new DuplicateUserExecption("Email already exists. Please use a different email address.");
        }
        dao.add(user);
        logger.info("User added: {}", user);
    }

    /**
     * Updates an existing user's name and email by ID.
     *
     * @param user The user data updated details.
     * @throws UserNotFoundException If no user exists with the given ID.
     * @throws DuplicateUserExecption If the updated email already exists for another user.
     */
    public void update(User user) throws UserNotFoundException, DuplicateUserExecption {
        User existingUser = dao.findById(user.getId());
        if (existingUser == null) {
            throw new UserNotFoundException("User with ID " + user.getId() + " does not exist.");
        }

        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            if (dao.checkEmailUniqueness(user.getEmail())) {
                throw new DuplicateUserExecption("Email already exists. Please use a different email address.");
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }

        dao.update(existingUser);
        logger.info("User updated: {}", existingUser);
    }
    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to delete.
     * @throws UserNotFoundException If no user exists with the given ID.
     */
    public void deleteById(int id) throws UserNotFoundException {
        User user = dao.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
         if(user.getTasks()!=null){
             for(Task task : user.getTasks()){
                 task.setUser(null);
             }
         }
        dao.delete(user);
        logger.info("User deleted: {}", user);
    }
    /**
     * Retrieves a list of all users.
     *
     * @return List of all users in the form of UserDto.
     * @throws UserNotFoundException If no users are found.
     */
    public List<User> getAll() throws UserNotFoundException {
        List<User> list = dao.getAll();
        if (list.isEmpty()) {
            throw new UserNotFoundException("No users found.");
        }

        return list;
    }
    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The UserDto with the specified ID.
     * @throws UserNotFoundException If no user exists with the given ID.
     */
    public User getById(int id) throws UserNotFoundException {
        User user = dao.findById(id);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + id + " not found.");
        }
        return user;
    }
    public void addTask(int id, Task task) {
        User user=dao.findById(id);
        if(user!=null){
            task.setDate(LocalDate.now());
           user.addTask(task);
           dao.update(user);
        }else{
            throw new UserNotFoundException("User Not Found");
        }
    }

    public boolean removeTaskById(int userId , int taskId){
        User user =getById(userId);
        Task task =user.getTasks().stream().
                filter( (t)-> t.getId()==taskId).findFirst().
                orElseThrow(()->new TaskNotExistExecption("Task Not exist"));
        task.setUser(null);
        dao.update(user);
        return true;

    }
    public void add(){

    }

}
