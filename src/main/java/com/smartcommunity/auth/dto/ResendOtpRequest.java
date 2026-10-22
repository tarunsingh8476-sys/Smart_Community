package com.smartcommunity.auth.dto;
import jakarta.validation.constraints.*;
public record ResendOtpRequest(@Email @NotBlank String email) {}
