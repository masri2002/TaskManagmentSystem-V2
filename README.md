
# Task Management System V2

Task Management System V2 is a Spring Boot application for managing tasks, projects, and users. This application uses PostgreSQL as its database.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
  - [Clone the Repository](#clone-the-repository)
  - [Configure PostgreSQL Database](#configure-postgresql-database)
  - [Configure the Application](#configure-the-application)
  - [Run the Application](#run-the-application)
- [Testing](#testing)
- [Endpoints](#endpoints)

## Features

- Create, update, delete, and retrieve projects
- Create, update, delete, and retrieve tasks
- Create, update, delete, and retrieve users
- Assign tasks to projects
- Assign users to projects

## Prerequisites

- Java 11 or later
- Maven 3.6 or later
- PostgreSQL 13 or later

## Setup

### Clone the Repository

```bash
git clone https://github.com/masri2002/TaskManagmentSystem-V2.git
cd TaskManagmentSystem-V2
```

### Configure PostgreSQL Database

1. **Install PostgreSQL**: Follow the instructions for your operating system to install PostgreSQL.

2. **Create a Database**:
   ```sql
   CREATE DATABASE task_management;
   ```

3. **Create a User**:
   ```sql
   CREATE USER task_user WITH ENCRYPTED PASSWORD 'password';
   ```

4. **Grant Privileges**:
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE task_management TO task_user;
   ```

### Configure the Application

1. **Update `application.properties`**: Update the `src/main/resources/application.properties` file with your PostgreSQL configuration:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
   spring.datasource.username=task_user
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   ```

### Run the Application

```bash
mvn spring-boot:run
```

The application should now be running on `http://localhost:8080`.

## Testing

To run the tests, use the following command:

```bash
mvn test
```

## Endpoints

- **Projects**
  - `POST /projects`: Create a new project
  - `GET /projects/{id}`: Get a project by ID
  - `PUT /projects`: Update a project
  - `DELETE /projects/{id}`: Delete a project

- **Tasks**
  - `POST /tasks`: Create a new task
  - `GET /tasks/{id}`: Get a task by ID
  - `PUT /tasks/{tId}/project/{pId}`: Assign a task to a project
  - `DELETE /tasks/{id}`: Delete a task

- **Users**
  - `POST /users`: Create a new user
  - `GET /users/{id}`: Get a user by ID
  - `PUT /users`: Update a user
  - `DELETE /users/{id}`: Delete a user
