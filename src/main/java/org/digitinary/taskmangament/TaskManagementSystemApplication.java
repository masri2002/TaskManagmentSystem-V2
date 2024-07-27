package org.digitinary.taskmangament;

import org.digitinary.taskmangament.dao.impl.ProjectDao;
import org.digitinary.taskmangament.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**.
 * main entry for project
 * @author ahmad almasri
 *
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
