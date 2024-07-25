package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when attempting to add a duplicate task.
 */
public class DuplicateTaskExecption extends RuntimeException {

    /**
     * Constructs a new DuplicateTaskExecption with the specified detail message.
     * @param message The detail message.
     */
    public DuplicateTaskExecption(String message) {
        super(message);
    }
}
