package com.smartcommunity.conversation;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ConversationParticipant {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Conversation conversation;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User user;

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public Conversation getConversation(){return conversation;}public void setConversation(Conversation v){conversation=v;}public com.smartcommunity.user.User getUser(){return user;}public void setUser(com.smartcommunity.user.User v){user=v;}
}
