package com.masri.TaskMangamentSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a user in the task management system.
 * Each user has a unique identifier, a name, and an email address.
 * A user can be associated with multiple projects.
 * @author ahmad almasri
 */
@Entity
@Table(name = "users")
public class User {


    private int id;

    @NotEmpty(message = "Name shouldn't be empty")

    private String name;

    @Email(message = "Email Must be Valid , example@email.com")

    private String email;


    private Set<Project> projects;

    /**
     * Default constructor for JPA.
     */
    public User() {
    }

    /**
     * Constructs a User with the specified name and email.
     *
     * @param name  the name of the user
     * @param email the email address of the user
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user ID
     */
    @Id
    @GeneratedValue(generator = "user-id-generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user-id-generator", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     *
     * @return the user's name
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the user's email address
     */
    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the set of projects associated with the user.
     *
     * @return the set of projects
     */
    @ManyToMany(mappedBy = "users", cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JsonBackReference
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the set of projects associated with the user.
     *
     * @param projects the set of projects
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
