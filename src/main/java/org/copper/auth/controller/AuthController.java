package org.copper.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.copper.auth.dto.request.UserRequest;
import org.copper.auth.dto.request.LoginRequest;
import org.copper.auth.dto.response.AuthResponse;
import org.copper.auth.dto.response.UserResponse;
import org.copper.auth.service.auth.AuthService;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws JsonProcessingException {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return new ResponseEntity<>(authService.register(userRequest), HttpStatus.CREATED);
    }
}
