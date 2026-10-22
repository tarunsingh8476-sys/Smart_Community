package com.smartcommunity.conversation;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ConversationMessage {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @Column(nullable=false,length=4000) private String content;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Conversation conversation;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User sender;

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public String getContent(){return content;}public void setContent(String v){content=v;}public Conversation getConversation(){return conversation;}public void setConversation(Conversation v){conversation=v;}public com.smartcommunity.user.User getSender(){return sender;}public void setSender(com.smartcommunity.user.User v){sender=v;}
}
