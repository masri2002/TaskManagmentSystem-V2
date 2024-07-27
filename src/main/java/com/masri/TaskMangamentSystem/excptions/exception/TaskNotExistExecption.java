package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when a task does not exist.
 */
public class TaskNotExistExecption extends RuntimeException {

    /**
     * Constructs a new TaskNotExistExecption with the specified detail message.
     * @param message The detail message.
     */
    public TaskNotExistExecption(String message) {
        super(message);
    }
}
