package org.digitinary.taskmangament;

import org.digitinary.taskmangament.dao.impl.ProjectDao;
import org.digitinary.taskmangament.entity.Project;
import org.digitinary.taskmangament.entity.User;
import org.digitinary.taskmangament.excptions.exception.DuplicateProjectsException;
import org.digitinary.taskmangament.excptions.exception.ProjectNotFoundExecption;
import org.digitinary.taskmangament.service.ProjectService;
import org.digitinary.taskmangament.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserService userService;



    @Test
    public void testAddProject() {
        Project project = new Project();
        project.setName("Test Project");
        projectService.addProject(project);

        Project fetchedProject = projectService.getById(project.getId());
        assertNotNull(fetchedProject);
        assertEquals("Test Project", fetchedProject.getName());
    }

    @Test
    public void testAddDuplicateProject() {
        Project project = new Project();
        project.setName("Test Project");
        projectService.addProject(project);

        Project duplicateProject = new Project();
        duplicateProject.setName("Test Project");

        assertThrows(DuplicateProjectsException.class, () -> {
            projectService.addProject(duplicateProject);
        });
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project();
        project.setName("Old Project");
        projectService.addProject(project);

        project.setName("Updated Project");
        projectService.updateProject(project);

        Project fetchedProject = projectService.getById(project.getId());
        assertEquals("Updated Project", fetchedProject.getName());
    }

    @Test
    public void testDeleteProject() {
        Project project = new Project();
        project.setName("To be deleted");
        projectService.addProject(project);

        projectService.deleteProject(project.getId());
        assertThrows(ProjectNotFoundExecption.class, () -> {
            projectService.getById(project.getId());
        });
    }

    @Test
    public void testAssignUserToProject() {
        Project project = new Project();
        project.setName("Project");
        projectService.addProject(project);

        User user = new User();
        user.setName("Test User");
        userService.addUser(user);

        projectService.assignUserToProject(project.getId(), user.getId());

        Project fetchedProject = projectService.getById(project.getId());
        List<User> users = fetchedProject.getUsers().stream().toList();
        assertTrue(users.contains(user));
    }
}
