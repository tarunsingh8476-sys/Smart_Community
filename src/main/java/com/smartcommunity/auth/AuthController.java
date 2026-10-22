package com.smartcommunity.auth;
import com.smartcommunity.auth.dto.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AuthService service;
 public AuthController(AuthService service){this.service=service;}
 @PostMapping("/signup") public Map<String,Object> signup(@Valid @RequestBody SignupRequest r){return service.signup(r);}
 @PostMapping("/verify-otp") public Map<String,Object> verify(@Valid @RequestBody VerifyOtpRequest r){return service.verify(r.email(),r.otp());}
 @PostMapping("/resend-otp") public Map<String,Object> resend(@Valid @RequestBody ResendOtpRequest r){return service.resend(r.email());}
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return service.login(r);}
 @GetMapping("/me") public AuthResponse me(Authentication a){return service.current(a.getName());}
}
