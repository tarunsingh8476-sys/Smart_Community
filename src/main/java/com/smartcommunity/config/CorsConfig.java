package com.smartcommunity.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;
import java.util.List;
@Configuration
public class CorsConfig {
 @Bean CorsFilter corsFilter(@Value("${app.frontend-url}") String frontend){
  CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(List.of(frontend));c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("Authorization","Content-Type"));c.setAllowCredentials(true);
  UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return new CorsFilter(s);
 }
}
