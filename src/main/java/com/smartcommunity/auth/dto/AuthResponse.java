package com.smartcommunity.auth.dto;
public record AuthResponse(String accessToken, String tokenType, java.util.Map<String,Object> user) {}
