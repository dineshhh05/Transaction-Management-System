package com.dinesh.tms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.tms.user.dto.CreateUserRequest;
import com.dinesh.tms.user.dto.UserResponse;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest req){
        User newUser = userService.createUser(req);

        UserResponse response = new UserResponse(newUser.getId(), newUser.getUsername(), newUser.getEmailID(), newUser.getFirstName(), newUser.getLastName());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }


    
}
