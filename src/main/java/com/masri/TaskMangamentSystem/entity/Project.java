package com.masri.TaskMangamentSystem.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a project in the task management system.
 * A project can have multiple tasks and users associated with it.
 * @author ahmad almasri
 */
@Entity
@Table(name = "projects")

public class Project {


    private int id;


    private String name;


    private String description;


    private Set<Task> tasks;


    private Set<User> users;

    /**
     * Default constructor for JPA.
     */
    public Project() {
    }

    /**
     * Constructs a Project with the specified name and description.
     *
     * @param name        the name of the project
     * @param description the description of the project
     */
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the unique identifier of the project.
     *
     * @return the project ID
     */
    @Id
    @GeneratedValue(generator = "project_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "project_id", sequenceName = "project_id_generator", allocationSize = 1)
    @Column(name = "project_id")
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the project.
     *
     * @param id the project ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the project.
     *
     * @return the project name
     */
    @Column(name = "project_name", nullable = false)
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name the project name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the project.
     *
     * @return the project description
     */
    @Column(name = "project_description")
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description the project description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the set of tasks associated with the project.
     *
     * @return the set of tasks
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    public Set<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the set of tasks associated with the project.
     *
     * @param tasks the set of tasks
     */
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets the set of users associated with the project.
     *
     * @return the set of users
     */
    @ManyToMany(fetch = FetchType.LAZY,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_projects",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the set of users associated with the project.
     *
     * @param users the set of users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Adds a task to the project.
     * Initializes the tasks set if it is null.
     *
     * @param task the task to add
     */
    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new HashSet<>();
        }
        tasks.add(task);
    }

    /**
     * Adds a user to the project.
     * Initializes the users set if it is null.
     *
     * @param user the user to add
     */
    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }
}