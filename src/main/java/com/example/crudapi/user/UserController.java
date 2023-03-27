package com.example.crudapi.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/users")
@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users= userService.getUsers();

        return users.isEmpty()? ResponseEntity.noContent().build() :ResponseEntity.ok(users);
    }

    @GetMapping(path = "{user_id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("user_id") Long id) {
        Optional<User> user = userService.getUser(id);
        return user.isPresent()? ResponseEntity.ok(user) : ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("{user_id}")
    public ResponseEntity<Void> updateUser(@PathVariable("user_id") Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUser(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            userService.updateUser(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
