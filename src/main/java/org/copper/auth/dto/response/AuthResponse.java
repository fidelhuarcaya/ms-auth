package org.copper.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String type;
    private String createAt;
    private String expirationDate;
    private String username;

}
