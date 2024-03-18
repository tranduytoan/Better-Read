package dbmsforeveread.foreveread.controller;

import dbmsforeveread.foreveread.model.User;
import dbmsforeveread.foreveread.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if(!Objects.equals(user.getPassword(), user.getRetypedPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match. Please try again.");
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser == null) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User userDetails) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setUsername(userDetails.getUsername());
        user.setId(userDetails.getId());

        User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }
}