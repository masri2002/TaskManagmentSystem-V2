package com.masri.TaskMangamentSystem.excptions.contolleradvicer;

import com.masri.TaskMangamentSystem.excptions.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling specific exceptions across the application.
 * <p>
 * This class uses Spring's {@link ControllerAdvice} to handle exceptions and return appropriate HTTP responses.
 * </p>
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles validation exceptions thrown during method argument validation.
     *
     * @param ex The MethodArgumentNotValidException containing details of validation errors.
     * @return A ResponseEntity containing a map of validation errors and an HTTP status of BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Get the specific validation error
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions for duplicate user emails.
     *
     * @param ex The DuplicateUserExecption containing the error message.
     * @return A ResponseEntity containing the error message and an HTTP status of CONFLICT.
     */
    @ExceptionHandler(DuplicateUserExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a duplicate resource
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleEmailExistExceptions(DuplicateUserExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions for duplicate tasks.
     *
     * @param ex The DuplicateTaskExecption containing the error message.
     * @return A ResponseEntity containing the error message and an HTTP status of CONFLICT.
     */
    @ExceptionHandler(DuplicateTaskExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a duplicate resource
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleTaskDuplicateExceptions(DuplicateTaskExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Task", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions for users not found.
     *
     * @param ex The UserNotFoundException containing the error message.
     * @return A ResponseEntity containing the error message and an HTTP status of CONFLICT.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a problem with user identification
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleUserNotFoundExceptions(UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("User", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    /**
     * Handles exceptions for tasks that do not exist.
     *
     * @param ex The TaskNotExistExecption containing the error message.
     * @return A ResponseEntity containing the error message and an HTTP status of CONFLICT.
     */
    @ExceptionHandler(TaskNotExistExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a problem with task identification
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleTaskNotFoundExceptions(TaskNotExistExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Task", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ProjectNotFoundExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a problem with task identification
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleProjectNotFoundExceptions(ProjectNotFoundExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Project", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
