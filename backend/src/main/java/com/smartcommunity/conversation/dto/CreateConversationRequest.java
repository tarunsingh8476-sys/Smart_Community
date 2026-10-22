package com.smartcommunity.conversation.dto;
import jakarta.validation.constraints.*;import java.util.*;
public record CreateConversationRequest(@Size(max=120) String title,@NotEmpty List<@Email String> participantEmails,boolean groupConversation){}
