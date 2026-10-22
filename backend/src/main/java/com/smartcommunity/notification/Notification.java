package com.smartcommunity.notification;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Notification {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User recipient;
 @Column(nullable=false,length=60) private String type;
 @Column(nullable=false,length=1000) private String description;
 @Column(nullable=false) private boolean read=false;

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public com.smartcommunity.user.User getRecipient(){return recipient;}public void setRecipient(com.smartcommunity.user.User v){recipient=v;}public String getType(){return type;}public void setType(String v){type=v;}
 public String getDescription(){return description;}public void setDescription(String v){description=v;}public boolean isRead(){return read;}public void setRead(boolean v){read=v;}
}
