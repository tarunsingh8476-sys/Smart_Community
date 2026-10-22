package com.smartcommunity.auth;
import com.smartcommunity.auth.dto.*;
import com.smartcommunity.email.BrevoEmailService;
import com.smartcommunity.user.*;
import com.smartcommunity.verification.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
@Service
public class AuthService {
 private final UserRepository users; private final EmailOtpRepository otps; private final PasswordEncoder encoder; private final JwtService jwt; private final BrevoEmailService email;
 private final SecureRandom random=new SecureRandom();
 public AuthService(UserRepository users,EmailOtpRepository otps,PasswordEncoder encoder,JwtService jwt,BrevoEmailService email){this.users=users;this.otps=otps;this.encoder=encoder;this.jwt=jwt;this.email=email;}
 @Transactional public Map<String,Object> signup(SignupRequest r){
  String e=norm(r.email()); if(users.existsByEmailIgnoreCase(e))throw new IllegalArgumentException("Email is already registered");
  User u=new User();u.setName(r.name().trim());u.setEmail(e);u.setPassword(encoder.encode(r.password()));u.setAuthProvider(User.AuthProvider.LOCAL);u.setEmailVerified(false);users.save(u);
  sendOtp(e);return Map.of("message","Signup saved. Check your email for the verification code.");
 }
 @Transactional public Map<String,Object> resend(String raw){
  String e=norm(raw);User u=users.findByEmailIgnoreCase(e).orElseThrow(()->new NoSuchElementException("Signup not found"));
  if(u.isEmailVerified())throw new IllegalArgumentException("Email is already verified");
  var prior=otps.findTopByEmailOrderByLastSentAtDesc(e);
  if(prior.isPresent()&&prior.get().getLastSentAt().plusSeconds(60).isAfter(Instant.now()))throw new IllegalArgumentException("Please wait before requesting another code");
  sendOtp(e);return Map.of("message","A new verification code has been sent if email delivery is configured.");
 }
 @Transactional public Map<String,Object> verify(String raw,String code){
  String e=norm(raw);User u=users.findByEmailIgnoreCase(e).orElseThrow(()->new NoSuchElementException("Signup not found"));
  EmailOtp o=otps.findTopByEmailOrderByLastSentAtDesc(e).orElseThrow(()->new IllegalArgumentException("Request a new verification code"));
  if(o.getExpiresAt().isBefore(Instant.now()))throw new IllegalArgumentException("Verification code expired");
  if(o.getAttempts()>=5)throw new IllegalArgumentException("Too many attempts; request a new code");
  o.setAttempts(o.getAttempts()+1);
  if(!encoder.matches(code,o.getOtpHash()))throw new IllegalArgumentException("Invalid verification code");
  u.setEmailVerified(true);otps.deleteByEmail(e);return Map.of("message","Email verified successfully");
 }
 public AuthResponse login(LoginRequest r){
  String e=norm(r.email());User u=users.findByEmailIgnoreCase(e).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
  if(u.getPassword()==null||!encoder.matches(r.password(),u.getPassword()))throw new IllegalArgumentException("Invalid email or password");
  if(!u.isEmailVerified())throw new IllegalArgumentException("Verify your email before logging in");
  return new AuthResponse(jwt.generate(e),"Bearer",profile(u));
 }
 public AuthResponse current(String email){User u=users.findByEmailIgnoreCase(email).orElseThrow(()->new NoSuchElementException("User not found"));return new AuthResponse("", "Bearer",profile(u));}
 private void sendOtp(String e){
  otps.deleteByEmail(e);String code=String.format("%06d",random.nextInt(1_000_000));EmailOtp o=new EmailOtp();o.setEmail(e);o.setOtpHash(encoder.encode(code));o.setExpiresAt(Instant.now().plusSeconds(300));o.setLastSentAt(Instant.now());o.setAttempts(0);otps.save(o);email.sendOtp(e,code);
 }
 private String norm(String e){return e.trim().toLowerCase(Locale.ROOT);}
 private Map<String,Object> profile(User u){Map<String,Object> m=new LinkedHashMap<>();m.put("id",u.getId());m.put("name",u.getName());m.put("email",u.getEmail());m.put("avatarUrl",u.getAvatarUrl());m.put("emailVerified",u.isEmailVerified());return m;}
}
