package org.copper.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String lastName;

    private String secondLastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Dni no puede estar vacio")
    private String dni;

    @NotBlank(message = "Password cannot be blank")
    private String password;

}

