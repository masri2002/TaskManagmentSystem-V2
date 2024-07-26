package com.masri.TaskMangamentSystem;

import com.masri.TaskMangamentSystem.service.ProjectService;
import com.masri.TaskMangamentSystem.service.TaskService;
import com.masri.TaskMangamentSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TaskMangamentSystemApplicationTests {
	private ProjectService projectService;
	private TaskService taskService;
	private UserService userService;

	@Autowired
	public TaskMangamentSystemApplicationTests(ProjectService projectService, TaskService taskService, UserService userService) {
		this.projectService = projectService;
		this.taskService = taskService;
		this.userService = userService;
	}

	@Test
	void contextLoads() {
		assertThat(projectService).isNotNull();
		assertThat(taskService).isNotNull();
		assertThat(userService).isNotNull();
	}

}
