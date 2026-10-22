package com.smartcommunity.user;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="users", indexes=@Index(name="idx_users_email",columnList="email",unique=true))
public class User {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 private String password;
 private String avatarUrl;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private AuthProvider authProvider=AuthProvider.LOCAL;
 @Column(nullable=false) private boolean emailVerified=false;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 @Column(nullable=false) private Instant updatedAt=Instant.now();
 @PreUpdate void touch(){updatedAt=Instant.now();}
 public enum AuthProvider { LOCAL, GOOGLE }
 public UUID getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPassword(){return password;} public void setPassword(String v){password=v;}
 public String getAvatarUrl(){return avatarUrl;} public void setAvatarUrl(String v){avatarUrl=v;}
 public AuthProvider getAuthProvider(){return authProvider;} public void setAuthProvider(AuthProvider v){authProvider=v;}
 public boolean isEmailVerified(){return emailVerified;} public void setEmailVerified(boolean v){emailVerified=v;}
 public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
