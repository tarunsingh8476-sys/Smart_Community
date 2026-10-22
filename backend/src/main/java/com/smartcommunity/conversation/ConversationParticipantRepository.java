package com.smartcommunity.conversation;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant,UUID>{
 List<ConversationParticipant> findByUserEmailIgnoreCase(String email);
 boolean existsByConversationIdAndUserEmailIgnoreCase(UUID id,String email);
 List<ConversationParticipant> findByConversationId(UUID id);
}
