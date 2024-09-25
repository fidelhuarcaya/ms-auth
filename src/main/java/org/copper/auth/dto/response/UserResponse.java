package org.copper.auth.dto.response;

import lombok.Data;
import org.copper.auth.entity.Role;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;
}

