package com.masri.TaskMangamentSystem.controller;

import com.masri.TaskMangamentSystem.entity.Task;
import com.masri.TaskMangamentSystem.entity.User;
import com.masri.TaskMangamentSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
     private final UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user){
        service.addUser(user);
        return ResponseEntity.ok("User Added Successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        return ResponseEntity.ok(service.getUserById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id){
        service.deleteUserById(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user){
        service.updateUser(user);
        return ResponseEntity.ok("User update Successfully");
    }
}
