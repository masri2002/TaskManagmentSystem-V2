package org.digitinary.taskmangament;

import org.digitinary.taskmangament.dao.impl.TaskDao;
import org.digitinary.taskmangament.entity.Project;
import org.digitinary.taskmangament.entity.Task;
import org.digitinary.taskmangament.excptions.exception.DuplicateTaskExecption;
import org.digitinary.taskmangament.excptions.exception.TaskNotExistExecption;
import org.digitinary.taskmangament.service.ProjectService;
import org.digitinary.taskmangament.service.TaskService;
import org.digitinary.taskmangament.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


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
