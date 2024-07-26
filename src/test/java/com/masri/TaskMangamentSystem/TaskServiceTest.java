package com.masri.TaskMangamentSystem;

import com.masri.TaskMangamentSystem.dao.impl.TaskDao;
import com.masri.TaskMangamentSystem.entity.Project;
import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
import com.masri.TaskMangamentSystem.service.ProjectService;
import com.masri.TaskMangamentSystem.service.TaskService;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test") // Use the test profile for the H2 database
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        taskDao.deleteAll();
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        taskService.addTask(task);

        Task fetchedTask = taskService.getById(task.getId());
        assertNotNull(fetchedTask);
        assertEquals("Test Task", fetchedTask.getTitle());
        assertEquals("Test Description", fetchedTask.getDescription());
    }

    @Test
    public void testAddDuplicateTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        taskService.addTask(task);

        Task duplicateTask = new Task();
        duplicateTask.setTitle("Test Task");

        assertThrows(DuplicateTaskExecption.class, () -> {
            taskService.addTask(duplicateTask);
        });
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task();
        task.setTitle("Old Task");
        task.setDescription("Old Description");
        taskService.addTask(task);

        task.setTitle("Updated Task");
        task.setDescription("Updated Description");
        taskService.updateTask(task);

        Task fetchedTask = taskService.getById(task.getId());
        assertEquals("Updated Task", fetchedTask.getTitle());
        assertEquals("Updated Description", fetchedTask.getDescription());
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setTitle("To be deleted");
        task.setDescription("To be deleted description");
        taskService.addTask(task);

        taskService.deleteTask(task.getId());
        assertThrows(TaskNotExistExecption.class, () -> {
            taskService.getById(task.getId());
        });
    }

    @Test
    public void testAssignTaskToProject() {
        Project project = new Project();
        project.setName("Project");
        projectService.addProject(project);

        Task task = new Task();
        task.setTitle("Task");
        taskService.addTask(task);

        taskService.assignTaskToProject(task.getId(), project.getId());

        Task fetchedTask = taskService.getById(task.getId());
        assertNotNull(fetchedTask.getProject());
        assertEquals(project.getId(), fetchedTask.getProject().getId());
    }
}
