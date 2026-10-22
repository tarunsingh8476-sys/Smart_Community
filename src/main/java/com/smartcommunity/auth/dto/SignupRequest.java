package com.smartcommunity.auth.dto;
import jakarta.validation.constraints.*;
public record SignupRequest(@NotBlank @Size(max=100) String name, @Email @NotBlank String email, @NotBlank @Size(min=8,max=72) String password) {}
