package com.smartcommunity.message;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Message {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @Column(nullable=false,length=4000) private String content;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User sender;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.channel.Channel channel;
 @Column(nullable=false) private Instant updatedAt=Instant.now();
 @PreUpdate void touch(){updatedAt=Instant.now();}

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public String getContent(){return content;}public void setContent(String v){content=v;}public com.smartcommunity.user.User getSender(){return sender;}public void setSender(com.smartcommunity.user.User v){sender=v;}
 public com.smartcommunity.channel.Channel getChannel(){return channel;}public void setChannel(com.smartcommunity.channel.Channel v){channel=v;}public Instant getUpdatedAt(){return updatedAt;}
}
