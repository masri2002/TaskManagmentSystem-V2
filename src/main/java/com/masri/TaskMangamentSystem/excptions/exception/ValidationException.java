package com.masri.TaskMangamentSystem.excptions.exception;

/**
 * Exception thrown when validation fails.
 * @author ahmad alsri
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new ValidationException with the specified detail message.
     * @param message The detail message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
