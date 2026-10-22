package com.smartcommunity.auth.dto;
import jakarta.validation.constraints.*;
public record VerifyOtpRequest(@Email @NotBlank String email, @Pattern(regexp="[0-9]{6}") String otp) {}
