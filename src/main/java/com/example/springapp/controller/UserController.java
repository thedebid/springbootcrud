package com.example.springapp.controller;

import com.example.springapp.dto.AuthenticationResponse;
import com.example.springapp.dto.LoginRequest;
import com.example.springapp.entity.User;
import com.example.springapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> userRegister(@RequestBody User user){
        return  new ResponseEntity<User>(userService.register(user), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public AuthenticationResponse userLogin(@RequestBody LoginRequest loginRequest) throws Exception {
        return userService.login(loginRequest);
    }
}
