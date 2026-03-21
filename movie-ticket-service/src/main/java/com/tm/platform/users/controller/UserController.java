package com.tm.platform.users.controller;

import com.tm.platform.common.dto.LoginRequestDTO;
import com.tm.platform.users.model.User;
import com.tm.platform.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public User findUser(@RequestParam String email){
       return userService.findUser(email);
    }

    @PostMapping("/register")
    public User addUser(@RequestBody User user){
       return userService.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // Returning as a JSON object is cleaner for frontend/Postman
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");

        return ResponseEntity.ok(response);
    }
}
