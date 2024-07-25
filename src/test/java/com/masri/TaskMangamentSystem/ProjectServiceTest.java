//package com.masri.TaskMangamentSystem;
//
//import com.masri.TaskMangamentSystem.excptions.*;
//import com.masri.TaskMangamentSystem.entity.Priority;
//import com.masri.TaskMangamentSystem.entity.Project;
//import com.masri.TaskMangamentSystem.entity.Status;
//import com.masri.TaskMangamentSystem.entity.Task;
//import com.masri.TaskMangamentSystem.Service.ProjectService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import java.time.LocalDate;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class ProjectServiceTest {
//
//    @Autowired
//    private ProjectService projectService;
//
//    private Project project1;
//    private Project project2;
//    private Task task1;
//    private Task task2;
//
//    @BeforeEach
//    public void setUp() {
//        project1 = new Project("Project 1", "Description 1");
//        project2 = new Project("Project 2", "Description 2");
//
//        task1 = new Task("Task 1", "Description 1", Status.COMPLETED, Priority.HIGH,LocalDate.now());
//        task2 = new Task("Task 2", "Description 2", Status.PENDING,Priority.LOW,LocalDate.now());
//    }
//
//    @Test
//    public void testAddProject() throws DuplicateProjectsException {
//        projectService.add(project1);
//        List<Project> projects = projectService.getAll();
//        assertTrue(projects.contains(project1));
//    }
//
//    @Test
//    public void testAddDuplicateProject() {
//        assertThrows(DuplicateProjectsException.class, () -> {
//            projectService.add(project1);
//            projectService.add(project1);
//        });
//    }
//
//    @Test
//    public void testGetProjectById() throws ProjectNotFoundExecption, DuplicateProjectsException {
//        projectService.add(project1);
//        Project foundProject = projectService.getById(project1.getId());
//        assertEquals(project1, foundProject);
//    }
//
//    @Test
//    public void testGetProjectByIdNotFound() {
//        assertThrows(ProjectNotFoundExecption.class, () -> {
//            projectService.getById(999);
//        });
//    }
//
//    @Test
//    public void testDeleteProjectById() throws ProjectNotFoundExecption, DuplicateProjectsException {
//        projectService.add(project1);
//        projectService.deleteById(project1.getId());
//        assertThrows(ProjectNotFoundExecption.class, () -> {
//            projectService.getById(project1.getId());
//        });
//    }
//
//    @Test
//    public void testAddTaskToProjectById() throws ProjectNotFoundExecption, DuplicateProjectsException,
//            TaskNotExistExecption, DuplicateTaskExecption {
//        projectService.add(project1);
//        projectService.addTaskToProjectById(project1.getId(), task1);
//        Project foundProject = projectService.getById(project1.getId());
//        assertTrue(foundProject.getTasks().contains(task1));
//    }
//
//    @Test
//    public void testUpdateProjectTaskPriorityById() throws ProjectNotFoundExecption, DuplicateProjectsException,
//            ProjectNotContainsTaskException, TaskNotExistExecption, DuplicateTaskExecption {
//        projectService.add(project1);
//        projectService.addTaskToProjectById(project1.getId(), task1);
//        projectService.updateProjectTaskPriorityById(project1.getId(), task1.getId(), Priority.LOW);
//        Project foundProject = projectService.getById(project1.getId());
//        Task updatedTask = foundProject.getTasks().stream().filter(task -> task.getId() == task1.getId()).findFirst().orElse(null);
//        assertNotNull(updatedTask);
//        assertEquals(Priority.LOW, updatedTask.getPriority());
//    }
//
//    @Test
//    public void testUpdateProjectTaskStatusById() throws ProjectNotFoundExecption, DuplicateProjectsException,
//            ProjectNotContainsTaskException, TaskNotExistExecption, DuplicateTaskExecption {
//        projectService.add(project1);
//        projectService.addTaskToProjectById(project1.getId(), task1);
//        projectService.updateProjectTaskStatusById(project1.getId(), task1.getId(), Status.COMPLETED);
//        Project foundProject = projectService.getById(project1.getId());
//        Task updatedTask = foundProject.getTasks().stream().filter(task -> task.getId() == task1.getId()).findFirst().orElse(null);
//        assertNotNull(updatedTask);
//        assertEquals(Status.COMPLETED, updatedTask.getStatus());
//    }
//
//    @Test
//    public void testCountTasksByStatus() throws ProjectNotFoundExecption, DuplicateProjectsException,
//            ProjectNotContainsTaskException, TaskNotExistExecption, DuplicateTaskExecption {
//        projectService.add(project1);
//        projectService.addTaskToProjectById(project1.getId(), task1);
//        projectService.addTaskToProjectById(project1.getId(), task2);
//        Long count = projectService.countTasksByStatus(project1.getId(), Status.PENDING);
//        assertEquals(1, count);
//    }
//
//    @Test
//    public void testCountTasksByStatusMap() throws ProjectNotFoundExecption, DuplicateProjectsException,
//            ProjectNotContainsTaskException, TaskNotExistExecption, DuplicateTaskExecption {
//        projectService.add(project1);
//        projectService.addTaskToProjectById(project1.getId(), task1);
//        projectService.addTaskToProjectById(project1.getId(), task2);
//
//        // Verify count of tasks by specific statuses
//        Long count = projectService.countTasksByStatus(project1.getId(), Status.COMPLETED);
//        Long countInProgress = projectService.countTasksByStatus(project1.getId(), Status.IN_PROGRESS);
//
//        assertEquals(1, count); // Expecting exactly 1 task with Status.COMPLETED
//        assertEquals(0,countInProgress);// Expecting exactly 0 task with Status.COMPLETED
//    }
//
//}