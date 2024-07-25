package com.masri.TaskMangamentSystem;
import com.masri.TaskMangamentSystem.entity.Priority;
import com.masri.TaskMangamentSystem.entity.Status;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateUserExecption;
import com.masri.TaskMangamentSystem.excptions.exception.UserNotFoundException;
import com.masri.TaskMangamentSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User testUser;
    private Task testTask;

    @BeforeEach
    public void setup() {
        // Setup initial test data
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");

        testTask = new Task();
        testTask.setTitle("Test Task");
        testTask.setPriority(Priority.MEDIUM);
        testTask.setStatus(Status.PENDING);
        testTask.setDueDate(LocalDate.now().plusDays(10));
        testTask.setDescription("Test task description");

    }

    @Test
    public void testAddUser() throws DuplicateUserExecption {
        userService.add(testUser);
        User retrievedUser = userService.getById(testUser.getId());
        assertNotNull(retrievedUser);
        assertEquals(testUser.getName(), retrievedUser.getName());
    }

    @Test
    public void testUpdateUser() throws DuplicateUserExecption, UserNotFoundException {
        userService.add(testUser);
        testUser.setName("Jane Doe");
        userService.update(testUser);
        User updatedUser = userService.getById(testUser.getId());
        assertEquals("Jane Doe", updatedUser.getName());
    }

    @Test
    public void testDeleteUser() throws DuplicateUserExecption, UserNotFoundException {
        userService.add(testUser);
        userService.deleteById(testUser.getId());
        assertThrows(UserNotFoundException.class, () -> userService.getById(testUser.getId()));
    }

    @Test
    public void testGetAllUsers() throws DuplicateUserExecption, UserNotFoundException {
        userService.add(testUser);
        List<User> users = userService.getAll();
        assertFalse(users.isEmpty());
    }

    @Test
    public void testAddTaskToUser() throws DuplicateUserExecption, UserNotFoundException {
        userService.add(testUser);
        userService.addTask(testUser.getId(), testTask);
        User userWithTask = userService.getById(testUser.getId());
        assertTrue(userWithTask.getTasks().stream().anyMatch(task -> task.getTitle().equals("Test Task")));
    }
//    @Test
//    public void testRemoveTask(){
//        userService.add(testUser);
//        userService.addTask(testUser,testTask);
//        Task task = user.getTasks().stream().filter((t)->t.getTitle().equals(testTask.getTitle())).findFirst().get();
//        userService.addTask(testUser.getId(),task);
//        assertEquals(userService.removeTaskById(testUser.getId(), task.getId()),true);
//    }

}