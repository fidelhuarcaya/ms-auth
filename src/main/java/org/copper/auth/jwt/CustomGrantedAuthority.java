package org.copper.auth.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class CustomGrantedAuthority implements GrantedAuthority{

	private final String role;
    private final String authority;


}
