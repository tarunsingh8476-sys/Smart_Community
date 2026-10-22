package com.smartcommunity.verification;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity
public class EmailOtp {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false) private String email;
 @Column(nullable=false) private String otpHash;
 @Column(nullable=false) private Instant expiresAt;
 @Column(nullable=false) private Instant lastSentAt;
 @Column(nullable=false) private int attempts=0;
 public UUID getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getOtpHash(){return otpHash;} public void setOtpHash(String v){otpHash=v;}
 public Instant getExpiresAt(){return expiresAt;} public void setExpiresAt(Instant v){expiresAt=v;}
 public Instant getLastSentAt(){return lastSentAt;} public void setLastSentAt(Instant v){lastSentAt=v;}
 public int getAttempts(){return attempts;} public void setAttempts(int v){attempts=v;}
}
