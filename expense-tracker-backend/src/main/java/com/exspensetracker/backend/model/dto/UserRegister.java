package com.exspensetracker.backend.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegister {
    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "This is not correct email format")
    private String email;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
