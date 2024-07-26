package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when a project is not found.
 * @author ahmad almasri
 */
public class ProjectNotFoundExecption extends RuntimeException {

    /**
     * Constructs a new ProjectNotFoundExecption with the specified detail message.
     * @param message The detail message.
     */
    public ProjectNotFoundExecption(String message) {
        super(message);
    }

}
