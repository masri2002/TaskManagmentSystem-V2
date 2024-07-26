package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when attempting to add a duplicate project.
 * @author ahmad almasri
 */
public class DuplicateProjectsException extends RuntimeException {

    /**
     * Constructs a new DuplicateProjectsException with the specified detail message.
     * @param message The detail message.
     */
    public DuplicateProjectsException(String message) {
        super(message);
    }
}
