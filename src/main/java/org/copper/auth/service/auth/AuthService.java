package org.copper.auth.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.copper.auth.dto.request.LoginRequest;
import org.copper.auth.dto.request.UserRequest;
import org.copper.auth.dto.response.UserResponse;
import org.copper.auth.dto.response.AuthResponse;



public interface AuthService {

    UserResponse register(UserRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest) throws JsonProcessingException;
}
