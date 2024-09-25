package org.copper.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getToken(UserDetails userDetails, long currentTime) throws JsonProcessingException;
    boolean isTokenExpired(String token);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
