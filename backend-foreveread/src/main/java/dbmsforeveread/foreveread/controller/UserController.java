package dbmsforeveread.foreveread.controller;

import dbmsforeveread.foreveread.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
//        User savedUser = userService.registerUser(user);
//        if(!Objects.equals(user.getPassword(), user.getRetypedPassword())) {
//            return ResponseEntity.badRequest().body("Passwords do not match. Please try again.");
//        }
//        return ResponseEntity.ok(savedUser);
//
//        //need more error handling
//
//    }

//    @PostMapping("/login")
//    public ResponseEntity<User> login(@Valid @RequestBody User user) {
//        User savedUser = userService.registerUser(user);
//        return ResponseEntity.ok(savedUser);
//        //need more error handling
//
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable String id) {
//        User user = userService.findById(id);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);
//    }
}