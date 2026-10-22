package com.smartcommunity.email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;
@Service
public class BrevoEmailService {
 @Value("${app.brevo.api-key:}") private String apiKey;
 @Value("${app.brevo.sender-email:}") private String senderEmail;
 private final RestClient client=RestClient.create();
 public void sendOtp(String to,String otp){
  if(apiKey.isBlank()||senderEmail.isBlank()) throw new IllegalStateException("Brevo is not configured; set BREVO_API_KEY and BREVO_SENDER_EMAIL");
  Map<String,Object> body=Map.of("sender",Map.of("email",senderEmail,"name","Smart Community"),
   "to",new Object[]{Map.of("email",to)},"subject","Smart Community email verification",
   "textContent","Your Smart Community verification code is "+otp+". It expires in five minutes.");
  client.post().uri("https://api.brevo.com/v3/smtp/email").header("api-key",apiKey)
   .contentType(MediaType.APPLICATION_JSON).body(body).retrieve().toBodilessEntity();
 }
}
