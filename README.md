
# Task Management System V2

Task Management System V2 is a Spring Boot application for managing tasks, projects, and users. This application uses PostgreSQL as its database.

## Table of Contents

- [Features](#features)
- [technologies](#technologies)
- [Setup](#setup)
  - [Clone the Repository](#clone-the-repository)
  - [Configure PostgreSQL Database](#configure-postgresql-database)
  - [Configure the Application](#configure-the-application)
  - [Run the Application](#run-the-application)
- [Testing](#testing)

## Features

- Create, update, delete, and retrieve projects
- Create, update, delete, and retrieve tasks
- Create, update, delete, and retrieve users
- Assign tasks to projects
- Assign users to projects
- process project tasks
## technologies

- Java 21
- Maven 
- PostgreSQL 16

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
   CREATE DATABASE your-db-name;
   ```

3. **Create a User**:
   ```sql
   CREATE USER task_user WITH ENCRYPTED PASSWORD 'your-db-password';
   ```

4. **Grant Privileges**:
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE yourDatabaseName TO your-db-username;
   ```

### Configure the Application

1. **Update `application.properties`**: Update the `src/main/resources/application.properties` file with your PostgreSQL configuration:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your-db-name
   spring.datasource.username=your-db_username
   spring.datasource.password=your-db-password
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

