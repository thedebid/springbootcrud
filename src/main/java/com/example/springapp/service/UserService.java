package com.example.springapp.service;

import com.example.springapp.dto.AuthenticationResponse;
import com.example.springapp.dto.LoginRequest;
import com.example.springapp.entity.User;
import com.example.springapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User register(User user) {
        return userRepository.save(user);
    }


    public AuthenticationResponse login(LoginRequest loginRequest) throws Exception {
        try {


            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                    loginRequest.getPassword()));
            System.out.println(authenticate);
            String originalInput = loginRequest.getUsername() + ":" + loginRequest.getPassword();
            String token = Base64.getEncoder().encodeToString(originalInput.getBytes());
            return new AuthenticationResponse(token, loginRequest.getUsername());
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

}
