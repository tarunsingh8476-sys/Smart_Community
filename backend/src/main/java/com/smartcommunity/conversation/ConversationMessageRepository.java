package com.smartcommunity.conversation;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface ConversationMessageRepository extends JpaRepository<ConversationMessage,UUID>{List<ConversationMessage> findTop50ByConversationIdOrderByCreatedAtDesc(UUID id);}
