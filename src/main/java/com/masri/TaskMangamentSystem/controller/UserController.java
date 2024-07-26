package com.masri.TaskMangamentSystem.controller;


import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to manage {@link User} entity.
 * @author ahmad almasri
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param service the service layer for managing users
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * Adds a new user.
     *
     * @param user the user to be added
     * @return a message indicating the user was added successfully
     */
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user){
        service.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Added Successfully");
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.FOUND).body((service.getUserById(id)));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a message indicating the user was deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id){
        service.deleteUserById(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }

    /**
     * Updates an existing user.
     *
     * @param user the user to be updated
     * @return a message indicating the user was updated successfully
     */
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user){
        service.updateUser(user);
        return ResponseEntity.ok("User Updated Successfully");
    }
}
