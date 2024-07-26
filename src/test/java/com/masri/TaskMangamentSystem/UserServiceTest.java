//package com.masri.TaskMangamentSystem;
//
//import com.masri.TaskMangamentSystem.dao.impl.UserDao;
//import com.masri.TaskMangamentSystem.entity.User;
//import com.masri.TaskMangamentSystem.excptions.exception.DuplicateUserExecption;
//import com.masri.TaskMangamentSystem.excptions.exception.UserNotFoundException;
//import com.masri.TaskMangamentSystem.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserDao userDao;
//    @BeforeEach
//    void setUp() {
//        // Clean up the database before each test
//        userDao.deleteAll();
//    }
//    @Test
//    void testAddUserSuccess() throws DuplicateUserExecption {
//
//
//        User user = new User("John Doe", "john@example.com");
//        userService.addUser(user);
//        User savedUser = userDao.findById(user.getId());
//        assertNotNull(savedUser);
//        assertEquals(user.getEmail(), savedUser.getEmail());
//    }
//
//    @Test
//    void testAddUserFailure() throws DuplicateUserExecption {
//        User user = new User("John Doe", "john@example.com");
//        userService.addUser(user); // Adding user to the DB to ensure email uniqueness
//        assertThrows(DuplicateUserExecption.class, () -> userService.addUser(user));
//    }
//
//    @Test
//    void testGetUserByIdSuccess() throws UserNotFoundException {
//
//        User user = new User("John Doe", "john@example.com");
//        userDao.add(user);
//
//
//        User result = userService.getUserById(user.getId());
//
//
//        assertEquals(user, result);
//    }
//    @Test
//    void testGetUserByIdFailure() throws UserNotFoundException {
//        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
//    }
//    @Test
//    void testUpdateUserSuccess() throws UserNotFoundException, DuplicateUserExecption {
//        // Arrange
//        User oldUser = new User("John Doe", "john@example.com");
//
//        userDao.add(oldUser);
//        User newUser = new User("John Smith", "john.smith@example.com");
//        newUser.setId(1);
//        userService.updateUser(newUser);
//        User updatedUser = userDao.findById(newUser.getId());
//        assertEquals(newUser.getName(), updatedUser.getName());
//        assertEquals(newUser.getEmail(), updatedUser.getEmail());
//    }
//
//    @Test
//    void testUpdateUserFailureNotFound() throws UserNotFoundException {
//        User user = new User("John Doe", "john@example.com");
//        user.setId(99);
//        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
//    }
//
//    @Test
//    void testUpdateUserFailureDuplicateEmail() throws UserNotFoundException, DuplicateUserExecption {
//        User oldUser = new User("John Doe", "john@example.com");
//        userDao.add(oldUser);
//        User newUser = new User("John Smith", "john.smith@example.com");
//        newUser.setId(1);
//        assertThrows(DuplicateUserExecption.class, () -> userService.updateUser(newUser));
//    }
//
//    @Test
//    void testDeleteUserByIdSuccess() throws UserNotFoundException {
//        User user = new User("John Doe", "john@example.com");
//        user.setId(1);
//        userDao.add(user);
//        userService.deleteUserById(user.getId());
//        assertNull(userDao.findById(user.getId()));
//    }
//
//    @Test
//    void testDeleteUserByIdFailure() throws UserNotFoundException {
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1));
//    }
//}