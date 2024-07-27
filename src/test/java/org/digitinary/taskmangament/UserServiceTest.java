package org.digitinary.taskmangament;

import org.digitinary.taskmangament.dao.impl.UserDao;
import org.digitinary.taskmangament.entity.User;
import org.digitinary.taskmangament.excptions.exception.DuplicateUserExecption;
import org.digitinary.taskmangament.excptions.exception.UserNotFoundException;
import org.digitinary.taskmangament.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

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
