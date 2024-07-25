package com.masri.TaskMangamentSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provides classes and interfaces for managing tasks within a task management system.
 * This package includes entities like {@link com.masri.TaskMangamentSystem.entity.Task},
 * {@link com.masri.TaskMangamentSystem.entity.Priority}, {@link com.masri.TaskMangamentSystem.entity.Status},
 * and services for task processing and project management.
 *
 * <p>Tasks can be added, updated, deleted, and processed asynchronously. They are categorized
 * by status (pending, in progress, completed) and priority (low, medium, high). Projects can
 * also be managed, allowing tasks to be assigned to specific projects.
 *
 * <p>The system is implemented using Spring Boot for dependency injection, MVC architecture,
 * and RESTful API development.
 *
 * @since 2024-07-15
 * @version Java.21
 */
@SpringBootApplication
public class TaskManagementSystemApplication {

	/**
	 * Main method to start the Spring Boot application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}

}
