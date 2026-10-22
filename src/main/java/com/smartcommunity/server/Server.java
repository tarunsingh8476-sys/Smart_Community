package com.smartcommunity.server;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Server {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @Column(nullable=false,length=100) private String name;
 @Column(length=1000) private String description;
 private String iconUrl;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.user.User owner;

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getIconUrl(){return iconUrl;} public void setIconUrl(String v){iconUrl=v;} public com.smartcommunity.user.User getOwner(){return owner;} public void setOwner(com.smartcommunity.user.User v){owner=v;}
}
