package com.smartcommunity.config;
import com.smartcommunity.user.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.security.oauth2.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(UserRepository users){
  return email->{var u=users.findByEmailIgnoreCase(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
   return User.withUsername(u.getEmail()).password(u.getPassword()==null?"":u.getPassword()).authorities("ROLE_USER").build();};
 }
 @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwt, OAuth2UserService<OAuth2UserRequest,OAuth2User> oauth, @Value("${app.frontend-url}") String frontend)throws Exception{
  http.csrf(csrf->csrf.disable()).cors(Customizer.withDefaults()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/signup","/api/auth/verify-otp","/api/auth/resend-otp","/api/auth/login","/oauth2/**","/login/oauth2/**","/error").permitAll().anyRequest().authenticated())
   .oauth2Login(o->o.userInfoEndpoint(i->i.userService(oauth)).successHandler((req,res,auth)->{
    res.sendRedirect(UriComponentsBuilder.fromUriString(frontend).path("/oauth/callback").build().toUriString());
   }))
   .exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication required")));
  http.addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class);return http.build();
 }
 @Bean OAuth2UserService<OAuth2UserRequest,OAuth2User> oauth2UserService(UserRepository users,PasswordEncoder encoder){
  DefaultOAuth2UserService delegate=new DefaultOAuth2UserService();
  return request->{OAuth2User google=delegate.loadUser(request);String email=google.getAttribute("email");
   Boolean verified=google.getAttribute("email_verified");
   if(email==null||!Boolean.TRUE.equals(verified))throw new OAuth2AuthenticationException(new org.springframework.security.oauth2.core.OAuth2Error("invalid_user_info"), "Google account email is not verified");
   String normalized=email.trim().toLowerCase(java.util.Locale.ROOT);
   var existing=users.findByEmailIgnoreCase(normalized);
   if(existing.isEmpty()){var u=new com.smartcommunity.user.User();u.setName(java.util.Objects.toString(google.getAttribute("name"),normalized));u.setEmail(normalized);u.setAvatarUrl(google.getAttribute("picture"));u.setAuthProvider(com.smartcommunity.user.User.AuthProvider.GOOGLE);u.setEmailVerified(true);users.save(u);}
   else if(existing.get().getAuthProvider()!=com.smartcommunity.user.User.AuthProvider.GOOGLE)throw new OAuth2AuthenticationException(new org.springframework.security.oauth2.core.OAuth2Error("invalid_user_info"), "This email belongs to a local account. Sign in with email and password.");
   return google;
  };
 }
}
