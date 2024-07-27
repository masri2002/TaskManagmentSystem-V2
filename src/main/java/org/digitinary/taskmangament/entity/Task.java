package org.digitinary.taskmangament.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import org.digitinary.taskmangament.enums.Priority;
import org.digitinary.taskmangament.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a task in the task management system.
 * Each task is associated with a project and has a priority, status, due date, and creation date.
 * @author ahmad almasri
 */
@Entity
@Table(name = "tasks")
public class Task {


    private int id;

    @NotEmpty(message = "Project title required")
    private String title;


    private String description;


    private Priority priority;


    private Status status;


    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;


    private LocalDate creationDate;


    private Project project;

    /**
     * Default constructor for JPA.
     */
    public Task() {
    }

    /**
     * Constructs a Task with the specified title, description, priority, status, and due date.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param priority    the priority of the task
     * @param status      the status of the task
     * @param dueDate     the due date of the task
     */
    public Task(String title, String description, Priority priority, Status status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
    }

    /**
     * Gets the unique identifier of the task.
     *
     * @return the task ID
     */
    @Id
    @GeneratedValue(generator = "task_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "task_id", sequenceName = "task_id_generator", allocationSize = 1)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the task ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the task.
     *
     * @return the task title
     */
    @Column(name = "task_title", nullable = false)
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title the task title
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the task.
     *
     * @return the task description
     */
    @Column(name = "task_description")
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the priority of the task.
     *
     * @return the task priority
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_priority")
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the task priority
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets the status of the task.
     *
     * @return the task status
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_status")
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     *
     * @param status the task status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the due date of the task.
     *
     * @return the task due date
     */
    @Column(name = "dead_line")
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the task.
     *
     * @param dueDate the task due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the creation date of the task.
     *
     * @return the task creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the task.
     *
     * @param creationDate the task creation date
     */
    @Column(name = "creation_date")
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the project associated with the task.
     *
     * @return the associated project
     */
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "project_id")
    @JsonBackReference
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with the task.
     *
     * @param project the project to associate with the task
     */
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", dueDate=" + dueDate +
                '}';
    }

}
