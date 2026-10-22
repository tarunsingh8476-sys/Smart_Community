package com.smartcommunity.channel.dto;
import jakarta.validation.constraints.*;
public record ChannelRequest(@NotBlank @Size(max=80) String name,@Size(max=500) String description){}
