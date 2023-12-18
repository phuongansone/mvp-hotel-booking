package com.hotel.mvp.hotelbooking.controller;

import com.hotel.mvp.hotelbooking.dto.RegisterUserRequest;
import com.hotel.mvp.hotelbooking.entity.User;
import com.hotel.mvp.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        // Validate request and perform user registration
        User user = userService.registerUser(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. ID: " + user.getUserId());
    }
}
