package com.smartcommunity.auth;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtService {
 @Value("${app.jwt.secret}") private String secret;
 @Value("${app.jwt.expiration}") private long expirationMs;
 private SecretKey key(){byte[] b=secret.getBytes(StandardCharsets.UTF_8); if(b.length<32) throw new IllegalStateException("JWT_SECRET must contain at least 32 bytes"); return Keys.hmacShaKeyFor(b);}
 public String generate(String email){long now=System.currentTimeMillis();return Jwts.builder().subject(email).issuedAt(new Date(now)).expiration(new Date(now+expirationMs)).signWith(key()).compact();}
 public String subject(String token){return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean valid(String token){try{subject(token);return true;}catch(JwtException|IllegalArgumentException e){return false;}}
}
