package com.smartcommunity.membership;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ServerMember {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.server.Server server;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User user;
 @Column(nullable=false) private String role="MEMBER";

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public com.smartcommunity.server.Server getServer(){return server;} public void setServer(com.smartcommunity.server.Server v){server=v;}
 public com.smartcommunity.user.User getUser(){return user;} public void setUser(com.smartcommunity.user.User v){user=v;}
 public String getRole(){return role;} public void setRole(String v){role=v;}
}
