package org.digitinary.taskmangament.excptions.exception;

/**
 * Exception thrown when a task is not found in a project.
 */
public class ProjectNotContainsTaskException extends RuntimeException {

    /**
     * Constructs a new ProjectNotContainsTaskException with the specified detail message.
     * @param message The detail message.
     */
    public ProjectNotContainsTaskException(String message) {
        super(message);
    }
}
