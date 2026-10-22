package com.smartcommunity.config;
import com.smartcommunity.auth.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final UserDetailsService details;
 public JwtAuthFilter(JwtService jwt,UserDetailsService details){this.jwt=jwt;this.details=details;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")&&SecurityContextHolder.getContext().getAuthentication()==null){
   String token=h.substring(7);
   if(jwt.valid(token)){try{String email=jwt.subject(token);UserDetails u=details.loadUserByUsername(email);
    var auth=new UsernamePasswordAuthenticationToken(u,null,u.getAuthorities());auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));SecurityContextHolder.getContext().setAuthentication(auth);
   }catch(RuntimeException ignored){}}
  }
  chain.doFilter(req,res);
 }
}
