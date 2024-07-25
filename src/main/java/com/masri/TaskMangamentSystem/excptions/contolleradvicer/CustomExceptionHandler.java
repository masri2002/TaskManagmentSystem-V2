package com.masri.TaskMangamentSystem.excptions.contolleradvicer;

import com.masri.TaskMangamentSystem.excptions.exception.DuplicateTaskExecption;
import com.masri.TaskMangamentSystem.excptions.exception.DuplicateUserExecption;
import com.masri.TaskMangamentSystem.excptions.exception.TaskNotExistExecption;
import com.masri.TaskMangamentSystem.excptions.exception.UserNotFoundException;
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

@ControllerAdvice
public class CustomExceptionHandler {

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
     * @param ex The DuplicateUserException containing the error message.
     * @return A ResponseEntity containing the error message.
     */
    @ExceptionHandler(DuplicateUserExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a duplicate resource
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleEmailExistExceptions(DuplicateUserExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateTaskExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a duplicate resource
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleTaskDuplicateExceptions(DuplicateTaskExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Task", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a duplicate resource
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleUserNotFoundExceptions(UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("User", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(TaskNotExistExecption.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Use CONFLICT to indicate a duplicate resource
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleTaskNotFoundExceptions(TaskNotExistExecption ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Task", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
