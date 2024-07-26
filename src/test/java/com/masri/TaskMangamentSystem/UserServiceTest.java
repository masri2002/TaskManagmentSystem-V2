package com.masri.TaskMangamentSystem;

import com.masri.TaskMangamentSystem.dao.impl.UserDao;
import com.masri.TaskMangamentSystem.entity.User;

import com.masri.TaskMangamentSystem.excptions.exception.DuplicateUserExecption;
import com.masri.TaskMangamentSystem.excptions.exception.UserNotFoundException;
import com.masri.TaskMangamentSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao.deleteAll();
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        userService.addUser(user);

        User fetchedUser = userService.getUserById(user.getId());
        assertNotNull(fetchedUser);
        assertEquals("Test User", fetchedUser.getName());
        assertEquals("testuser@example.com", fetchedUser.getEmail());
    }

    @Test
    public void testAddDuplicateUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        userService.addUser(user);

        User duplicateUser = new User();
        duplicateUser.setName("Duplicate User");
        duplicateUser.setEmail("testuser@example.com");

        assertThrows(DuplicateUserExecption.class, () -> {
            userService.addUser(duplicateUser);
        });
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("Old Name");
        user.setEmail("oldemail@example.com");
        userService.addUser(user);

        user.setName("New Name");
        user.setEmail("newemail@example.com");
        userService.updateUser(user);

        User fetchedUser = userService.getUserById(user.getId());
        assertEquals("New Name", fetchedUser.getName());
        assertEquals("newemail@example.com", fetchedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("To be deleted");
        user.setEmail("delete@example.com");
        userService.addUser(user);

        userService.deleteUserById(user.getId());
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(user.getId());
        });
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        userService.addUser(user);

        User fetchedUser = userService.getUserById(user.getId());
        assertNotNull(fetchedUser);
        assertEquals(user.getId(), fetchedUser.getId());
    }
}
