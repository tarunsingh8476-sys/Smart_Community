package com.smartcommunity.conversation.dto;
import jakarta.validation.constraints.*;
public record ConversationMessageRequest(@NotBlank @Size(max=4000) String content){}
