package org.digitinary.taskmangament.entity;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}