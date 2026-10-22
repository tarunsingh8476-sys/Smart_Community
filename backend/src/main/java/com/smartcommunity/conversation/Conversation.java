package com.smartcommunity.conversation;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Conversation {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @Column(nullable=false,length=120) private String title;
 @Column(nullable=false) private boolean groupConversation=false;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User createdBy;

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public String getTitle(){return title;}public void setTitle(String v){title=v;}public boolean isGroupConversation(){return groupConversation;}public void setGroupConversation(boolean v){groupConversation=v;}
 public com.smartcommunity.user.User getCreatedBy(){return createdBy;}public void setCreatedBy(com.smartcommunity.user.User v){createdBy=v;}
}
