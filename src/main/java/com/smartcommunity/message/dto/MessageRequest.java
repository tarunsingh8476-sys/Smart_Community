package com.smartcommunity.message.dto;
import jakarta.validation.constraints.*;
public record MessageRequest(@NotBlank @Size(max=4000) String content){}
