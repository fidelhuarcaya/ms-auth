package org.copper.auth.service.auth;

import jakarta.transaction.Transactional;
import org.copper.auth.common.RoleCode;
import org.copper.auth.common.StatusCode;
import org.copper.auth.dto.request.LoginRequest;
import org.copper.auth.dto.request.UserRequest;
import org.copper.auth.dto.response.AuthResponse;
import org.copper.auth.dto.response.UserResponse;
import org.copper.auth.entity.Role;
import org.copper.auth.entity.Status;
import org.copper.auth.entity.User;
import org.copper.auth.entity.UserRole;
import org.copper.auth.exception.RequestException;
import org.copper.auth.jwt.JwtService;
import org.copper.auth.mapper.UserMapper;
import org.copper.auth.repository.RoleRepository;
import org.copper.auth.repository.StatusRepository;
import org.copper.auth.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.copper.auth.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Value("${jwt.expiration-time}")
    private long expirationTime;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public UserResponse register(UserRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Status status = statusRepository.findByCode(StatusCode.ACTIVE);
        User user = userMapper.dtoToEntity(registerRequest);
        user.setStatus(status);

        List<UserRole> userRoleList = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        Role role = roleRepository.findByCode(RoleCode.USER).orElseThrow(() -> new RuntimeException("El role no existe"));
        userRole.setRole(role);
        userRoleList.add(userRole);
        user.setUserRoles(userRoleList);
        user = userRepository.save(user);

        return userMapper.entityToDto(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) throws JsonProcessingException {
        if (!userRepository.existsByEmail(loginRequest.getEmail())) {
            throw new RequestException("User not exist.");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        String formattedCurrentDate = dateFormat.format(currentDate);

        Date expirationDate = new Date(currentTime + expirationTime);
        String formattedExpirationDate = dateFormat.format(expirationDate);
        return AuthResponse.builder()
                .token(jwtService.getToken(userDetails, currentTime))
                .type(Jwts.jwsHeader().getType())
                .createAt(formattedCurrentDate)
                .expirationDate(formattedExpirationDate)
                .username(userDetails.getUsername())
                .build();
    }

    private UserDetails buildUserDetails(User user) {

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>());
    }
}
