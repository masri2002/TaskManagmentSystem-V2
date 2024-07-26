package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when a task is not found in a project.
 * @author ahmad almasr
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
