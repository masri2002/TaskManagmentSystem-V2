//package com.masri.TaskMangamentSystem;
//
//import com.masri.TaskMangamentSystem.dao.impl.TaskDao;
//import com.masri.TaskMangamentSystem.entity.Project;
//import com.masri.TaskMangamentSystem.entity.Task;
//import com.masri.TaskMangamentSystem.entity.Priority;
//import com.masri.TaskMangamentSystem.entity.Status;
//import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
//import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
//import com.masri.TaskMangamentSystem.service.ProjectService;
//import com.masri.TaskMangamentSystem.service.TaskService;
//import com.masri.TaskMangamentSystem.service.UserService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.time.LocalDate;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@Transactional
//class TaskServiceTest {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private TaskDao taskDao;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    void testAddTask_TaskDoesNotExist() {
//        // Arrange
//        Task task = new Task("New Task", "Task Description", Priority.HIGH, Status.PENDING, LocalDate.now());
//
//        // Act
//        taskService.addTask(task);
//
//        // Assert
//        Task savedTask = taskDao.findById(task.getId());
//        assertNotNull(savedTask);
//        assertEquals(task.getTitle(), savedTask.getTitle());
//    }
//
//    @Test
//    void testAddTask_TaskAlreadyExists() {
//        // Arrange
//        Task task = new Task("Existing Task", "Task Description", Priority.HIGH, Status.PENDING, LocalDate.now());
//        taskDao.add(task);
//
//        // Act & Assert
//        DuplicateTaskExecption exception = assertThrows(DuplicateTaskExecption.class, () -> taskService.addTask(task));
//        assertEquals("Task already exists", exception.getMessage());
//    }
//
//    @Test
//    void testGetById_TaskExists() {
//        // Arrange
//        Task task = new Task("Task", "Description", Priority.HIGH, Status.PENDING, LocalDate.now());
//        taskDao.add(task);
//
//        // Act
//        Task result = taskService.getById(task.getId());
//
//        // Assert
//        assertEquals(task, result);
//    }
//
//    @Test
//    void testGetById_TaskDoesNotExist() {
//        // Act & Assert
//        TaskNotExistExecption exception = assertThrows(TaskNotExistExecption.class, () -> taskService.getById(1));
//        assertEquals("Task does not exist", exception.getMessage());
//    }
//
//    @Test
//    void testDeleteTask_Success() {
//        // Arrange
//        Task task = new Task("Task", "Description", Priority.HIGH, Status.PENDING, LocalDate.now());
//        taskDao.add(task);
//
//        // Act
//        taskService.deleteTask(task.getId());
//
//        // Assert
//        assertNull(taskDao.findById(task.getId()));
//    }
//
//    @Test
//    void testDeleteTask_TaskDoesNotExist() {
//        // Act & Assert
//        TaskNotExistExecption exception = assertThrows(TaskNotExistExecption.class, () -> taskService.deleteTask(1));
//        assertEquals("Task does not exist", exception.getMessage());
//    }
//
//    @Test
//    void testUpdateTask_Success() {
//        // Arrange
//        Task oldTask = new Task("Old Task", "Old Description", Priority.HIGH, Status.PENDING, LocalDate.now());
//        taskDao.add(oldTask);
//        Task newTask = new Task("New Task", "New Description", Priority.MEDIUM, Status.IN_PROGRESS, LocalDate.now().plusDays(1));
//        newTask.setId(oldTask.getId());
//
//        // Act
//        taskService.updateTask(newTask);
//
//        // Assert
//        Task updatedTask = taskDao.findById(newTask.getId());
//        assertEquals(newTask.getTitle(), updatedTask.getTitle());
//        assertEquals(newTask.getDescription(), updatedTask.getDescription());
//        assertEquals(newTask.getPriority(), updatedTask.getPriority());
//        assertEquals(newTask.getStatus(), updatedTask.getStatus());
//        assertEquals(newTask.getDueDate(), updatedTask.getDueDate());
//        assertEquals(newTask.getProject(), updatedTask.getProject());
//    }
//
//    @Test
//    void testUpdateTask_TaskDoesNotExist() {
//        // Arrange
//        Task task = new Task("Task", "Description", Priority.HIGH, Status.PENDING, LocalDate.now());
//        task.setId(1);
//
//        // Act & Assert
//        TaskNotExistExecption exception = assertThrows(TaskNotExistExecption.class, () -> taskService.updateTask(task));
//        assertEquals("Task does not exist", exception.getMessage());
//    }
//
//
//}