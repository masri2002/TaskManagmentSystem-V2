package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when attempting to add a duplicate user.
 */
public class DuplicateUserExecption extends RuntimeException {

    /**
     * Constructs a new DuplicateUserExecption with the specified detail message.
     * @param message The detail message.
     */
    public DuplicateUserExecption(String message) {
        super(message);
    }
}
