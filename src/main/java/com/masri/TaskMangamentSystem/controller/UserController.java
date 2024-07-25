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
    public UserController(UserService dao) {
        this.service = dao;
    }
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        service.add(user);
        return ResponseEntity.ok("User Added");
    }
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
         return ResponseEntity.ok(service.getAll());
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user){
         service.update(user);
         return ResponseEntity.ok("User Updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable int id){
         service.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        return ResponseEntity.ok(service.getById(id));
    }
    @PostMapping("/{id}/task")
    public ResponseEntity<?> addTask(@PathVariable int id ,@RequestBody Task task){
         service.addTask(id,task);
         return ResponseEntity.ok("added ");
    }
    @PutMapping("{userId}/task/{taskId}")
    public ResponseEntity<?> removeTask(@PathVariable int userId , @PathVariable int taskId){
         service.removeTaskById(userId,taskId);
         return ResponseEntity.ok("Done");
    }
}
