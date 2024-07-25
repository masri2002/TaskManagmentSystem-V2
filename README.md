# Task Management System
This Project applies Object-Oriented Programming (OOP), Lambdas and Streams, and Multi-threading in Java. This project aims to reinforce your understanding and provide hands-on experience in implementing these core Java features while adhering to clean code practices and standards.
## Description
This Task Management System is designed to manage tasks, users, and projects with various statuses and priorities. Users can perform operations such as adding, updating, deleting, and processing tasks asynchronously. The system also supports project management, assigning tasks to specific projects. Tasks can be tracked by status (pending, in progress, completed) and priority (low, medium, high).

## Technologies Used
- Java 21
- Spring Boot
- Maven
- REST Api's

## Features
- CRUD operations for tasks
- Asynchronous task processing using ExecutorService
- Project management with task assignment
- Task status and priority tracking
- RESTful API for task management operations

## Packages

### com.masri.TaskManagementSystem
- **TaskManagementSystemApplication**: Main class to start the Spring Boot application.

### com.masri.TaskManagementSystem.Controller
- **TaskController**: REST API controller for managing tasks.
- **ProjectController**:REST API controller for managing projects and their tasks
- **UserController**:REST API controller for managing users
### com.masri.TaskManagementSystem.DTO
- **TaskDTO**: Data Transfer Object for tasks.

### com.masri.TaskManagementSystem.Exception
- A lot of classes to handle Different exceptions

### com.masri.TaskManagementSystem.Model
- **Task**: Represents a task with attributes such as id, title, description, priority, status, and Due date.
- **Priority**: Enum defining task priorities (LOW, MEDIUM, HIGH).
- **Status**: Enum defining task statuses (PENDING, IN_PROGRESS, COMPLETED).
- **Project**: Represents a Project with attributes such as id, title, description, and TreeSet of tasks

### com.masri.TaskManagementSystem.Service.impl
- **TaskProcessor**: Service for asynchronous task processing using ExecutorService.
- **TaskService**: Service for managing tasks including CRUD operations.
- **ProjectService**: Service for managing projects including CRUD operations and task assignment.
- **UserService** : Service for managing User including CRUD operations.

### com.masri.TaskManagementSystem.Service
- **ServiceInterface**: Generic service interface defining CRUD operations.

### com.masri.TaskManagementSystem.Model
- **User**: Represents a user of the system with attributes such as id, username, email, and roles.

## How to Run
1. Ensure you have Java 21 and Maven installed.
2. Clone the repository: `git clone <repository_url>`
3. Navigate to the project directory: `cd TaskManagementSystem`
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`
6. Access the application using the following URL: `http://localhost:8080`

## Notes
1. You Can Read JavaDocs for Better Understanding  
2. This project is just Demonstration to achieve my internship task
3. Maybe have some logic errors its just trying to use all topic I have learnd   
