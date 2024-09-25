package org.copper.auth.mapper;


import org.copper.auth.dto.request.UserRequest;
import org.copper.auth.dto.response.AuthResponse;
import org.copper.auth.dto.response.UserResponse;
import org.copper.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User dtoToEntity(UserRequest registerRequest);
    UserResponse entityToDto(User user);
    AuthResponse entityToAuthDto(User user);
}
