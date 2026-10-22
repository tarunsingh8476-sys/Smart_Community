package com.smartcommunity.channel;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Channel {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;

 @Column(nullable=false,length=80) private String name;
 @Column(length=500) private String description;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private com.smartcommunity.server.Server server;

 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 public UUID getId(){return id;} public Instant getCreatedAt(){return createdAt;}

 public String getName(){return name;}public void setName(String v){name=v;}public String getDescription(){return description;}public void setDescription(String v){description=v;}
 public com.smartcommunity.server.Server getServer(){return server;}public void setServer(com.smartcommunity.server.Server v){server=v;}
}
