package org.digitinary.taskmangament.controller;

import org.digitinary.taskmangament.entity.User;
import org.digitinary.taskmangament.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles HTTP requests for managing users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param service the service for managing users
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Adds a new user.
     *
     * @param user the user to be added
     * @return a message indicating success
     */
    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        service.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return a message indicating success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        service.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @return a message indicating success
     */
    @PutMapping
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user) {
        service.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }
}
