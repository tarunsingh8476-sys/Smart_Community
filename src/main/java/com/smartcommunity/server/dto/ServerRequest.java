package com.smartcommunity.server.dto;
import jakarta.validation.constraints.*;
public record ServerRequest(@NotBlank @Size(max=100) String name,@Size(max=1000) String description,String iconUrl) {}
