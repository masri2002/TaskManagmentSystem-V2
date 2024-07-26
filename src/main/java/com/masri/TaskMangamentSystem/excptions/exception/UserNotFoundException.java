package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when a user is not found.
 * @author ahmad almasri
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
