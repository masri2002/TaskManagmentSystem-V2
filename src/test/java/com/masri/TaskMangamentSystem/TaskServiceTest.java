//package com.masri.TaskMangamentSystem;
//
//import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
//import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
//import com.masri.TaskMangamentSystem.entity.Priority;
//import com.masri.TaskMangamentSystem.entity.Status;
//import com.masri.TaskMangamentSystem.entity.Task;
//import com.masri.TaskMangamentSystem.Service.TaskService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TaskServiceTest {
//
//    private TaskService taskService;
//
//    @BeforeEach
//    void setUp() {
//        taskService = new TaskService();
//    }
//
//    @Test
//    void testAddTask_Successful() throws DuplicateTaskExecption {
//        // Create a new task
//        Task newTask = new Task("Test Task", "Description", Status.PENDING, Priority.LOW, LocalDate.now());
//
//        // Add the task
//        boolean result = taskService.add(newTask);
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddTask_DuplicateTaskException() throws DuplicateTaskExecption {
//        // Create a task that already exists
//        Task existingTask = new Task("DevOps", "You Have To Environments in Docker File ", Status.PENDING, Priority.HIGH, LocalDate.now());
//
//        // Add the existing task first
//        taskService.add(existingTask);
//
//        // Try to add the same task again
//        DuplicateTaskExecption exception = assertThrows(DuplicateTaskExecption.class, () -> taskService.add(existingTask));
//
//        assertEquals("Task Already Exists", exception.getMessage());
//    }
//
//    @Test
//    void testUpdateTask_Successful() throws TaskNotExistExecption {
//        // Add a task
//        Task taskToUpdate = new Task("Test Task", "Description", Status.PENDING, Priority.LOW, LocalDate.now());
//        taskService.add(taskToUpdate);
//
//        // Modify the task
//        taskToUpdate.setStatus(Status.IN_PROGRESS);
//        taskService.update(taskToUpdate);
//
//        // Retrieve the updated task
//        Task updatedTask = taskService.getById(taskToUpdate.getId());
//
//        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());
//    }
//
//    @Test
//    void testDeleteTask_Successful() throws TaskNotExistExecption {
//        // Add a task
//        Task taskToDelete = new Task("Test Task", "Description", Status.PENDING, Priority.LOW, LocalDate.now());
//        taskService.add(taskToDelete);
//
//        // Delete the task
//        taskService.deleteById(taskToDelete.getId());
//
//        // Ensure the task is deleted
//        assertThrows(TaskNotExistExecption.class, () -> taskService.getById(taskToDelete.getId()));
//    }
//
//    @Test
//    void testGetAllTasks() {
//        // Add some tasks
//        taskService.add(new Task("Task 1", "Description 1", Status.PENDING, Priority.LOW, LocalDate.now()));
//        taskService.add(new Task("Task 2", "Description 2", Status.IN_PROGRESS, Priority.MEDIUM, LocalDate.now()));
//
//        // Retrieve all tasks
//        List<Task> allTasks = new ArrayList<>(taskService.getAll());
//
//        assertEquals(2, allTasks.size());
//    }
//
//    @Test
//    void testGetTaskById_Successful() throws TaskNotExistExecption {
//        // Add a task
//        Task taskToAdd = new Task("Test Task", "Description", Status.PENDING, Priority.LOW, LocalDate.now());
//        taskService.add(taskToAdd);
//
//        // Retrieve the task by ID
//        Task retrievedTask = taskService.getById(taskToAdd.getId());
//
//        assertEquals(taskToAdd, retrievedTask);
//    }
//
//    @Test
//    void testGetTaskById_TaskNotExistException() {
//        // Attempt to retrieve a nonexistent task
//        int nonExistentId = 999;
//        TaskNotExistExecption exception = assertThrows(TaskNotExistExecption.class, () -> taskService.getById(nonExistentId));
//
//        assertEquals("Task with ID " + nonExistentId + " not found.", exception.getMessage());
//    }
//}