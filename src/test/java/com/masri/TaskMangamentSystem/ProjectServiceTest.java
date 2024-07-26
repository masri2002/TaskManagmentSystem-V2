//package com.masri.TaskMangamentSystem;
//
//import com.masri.TaskMangamentSystem.dao.impl.ProjectDao;
//import com.masri.TaskMangamentSystem.entity.Project;
//import com.masri.TaskMangamentSystem.entity.Task;
//import com.masri.TaskMangamentSystem.entity.User;
//import com.masri.TaskMangamentSystem.excptions.exception.DuplicateProjectsException;
//import com.masri.TaskMangamentSystem.excptions.exception.ProjectNotFoundExecption;
//import com.masri.TaskMangamentSystem.service.ProjectService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//class ProjectServiceTest {
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private ProjectDao projectDao;
//
//    @BeforeEach
//    void setUp() {
//        // Clean up the database before each test
//        projectDao.deleteAll();
//    }
//
//    @Test
//    void testAddProject_ProjectDoesNotExist() {
//        // Arrange
//        Project project = new Project("New Project", "Project Description");
//
//        // Act
//        projectService.addProject(project);
//
//        // Assert
//        assertNotNull(projectDao.findById(project.getId()));
//    }
//
//    @Test
//    void testAddProject_ProjectAlreadyExists() {
//        // Arrange
//        Project project = new Project("Existing Project", "Project Description");
//        projectDao.add(project);  // Save the project using DAO
//
//        // Act & Assert
//        DuplicateProjectsException exception = assertThrows(DuplicateProjectsException.class, () -> projectService.addProject(project));
//        assertEquals("Project Already Exists", exception.getMessage());
//    }
//
//    @Test
//    void testGetById_ProjectExists() {
//        // Arrange
//        Project project = new Project("Project", "Description");
//        projectDao.add(project);
//
//        // Act
//        Project result = projectService.getById(project.getId());
//
//        // Assert
//        assertEquals(project, result);
//    }
//
//    @Test
//    void testGetById_ProjectDoesNotExist() {
//        // Act & Assert
//        ProjectNotFoundExecption exception = assertThrows(ProjectNotFoundExecption.class, () -> projectService.getById(1));
//        assertEquals("Project Not Found", exception.getMessage());
//    }
//
//    @Test
//    void testUpdateProject_Success() {
//        // Arrange
//        Project oldProject = new Project("Old Project", "Old Description");
//        projectDao.add(oldProject);
//        Project newProject = new Project("New Project", "New Description");
//        newProject.setId(oldProject.getId());
//
//        // Act
//        projectService.updateProject(newProject);
//
//        // Assert
//        Project updatedProject = projectDao.findById(oldProject.getId());
//        assertNotNull(updatedProject);
//        assertEquals(newProject.getName(), updatedProject.getName());
//        assertEquals(newProject.getDescription(), updatedProject.getDescription());
//    }
//
//    @Test
//    void testUpdateProject_ProjectDoesNotExist() {
//        // Arrange
//        Project project = new Project("Project", "Description");
//        project.setId(1);
//
//        // Act & Assert
//        ProjectNotFoundExecption exception = assertThrows(ProjectNotFoundExecption.class, () -> projectService.updateProject(project));
//        assertEquals("Project Not Found", exception.getMessage());
//    }
//
//    @Test
//    void testUpdateProject_DuplicateTitle() {
//        // Arrange
//        Project oldProject = new Project("Old Project", "Old Description");
//        projectDao.add(oldProject);
//        Project newProject = new Project("New Project", "New Description");
//        newProject.setId(oldProject.getId());
//        projectDao.add(new Project("New Project", "Another Description")); // Save a project with the same title
//
//        // Act & Assert
//        DuplicateProjectsException exception = assertThrows(DuplicateProjectsException.class, () -> projectService.updateProject(newProject));
//        assertEquals("Project With This Title Already Exists", exception.getMessage());
//    }
//
//    @Test
//    void testDeleteProject_Success() {
//        // Arrange
//        Project project = new Project("Project", "Description");
//        projectDao.add(project);
//
//        // Act
//        projectService.deleteProject(project.getId());
//
//        // Assert
//        assertEquals(null,projectDao.findById(project.getId()));
//    }
//
//    @Test
//    void testDeleteProject_ProjectDoesNotExist() {
//        // Act & Assert
//        ProjectNotFoundExecption exception = assertThrows(ProjectNotFoundExecption.class, () -> projectService.deleteProject(1));
//        assertEquals("Project Not Found", exception.getMessage());
//    }
//
//    @Test
//    void testGetProjectDetails_ProjectExists() {
//        // Arrange
//        Project project = new Project("Project", "Description");
//         projectDao.add(project);
//
//        // Act
//        Project result = projectService.getProjectDetails(project.getId());
//
//        // Assert
//        assertEquals(project, result);
//    }
//
//    @Test
//    void testGetProjectDetails_ProjectDoesNotExist() {
//        // Act & Assert
//        ProjectNotFoundExecption exception = assertThrows(ProjectNotFoundExecption.class, () -> projectService.getProjectDetails(1));
//        assertEquals("Project Not Found", exception.getMessage());
//    }
//
//    @Test
//    void testGetProjectUserById() {
//        // Arrange
//        Project project = new Project("Project", "Description");
//       projectDao.add(project);
//        User user = new User("User", "user@email.com");
//        project.addUser(user);
//        projectDao.add(project);
//
//        // Act
//        List<User> users = projectService.getProjectUserById(project.getId());
//
//        // Assert
//        assertEquals(1, users.size());
//        assertTrue(users.contains(user));
//    }
//
//    @Test
//    void testGetProjectTaskById() {
//        // Arrange
//        Project project = new Project("Project", "Description");
//        projectDao.add(project);
//        Task task = new Task("Task", "Task Description", null, null, null);
//        project.addTask(task);
//        projectDao.add(project);
//
//        // Act
//        List<Task> tasks = projectService.getProjectTaskById(project.getId());
//
//        // Assert
//        assertEquals(1, tasks.size());
//        assertTrue(tasks.contains(task));
//    }
//}
