package com.smartcommunity.notification;
import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/notifications")
public class NotificationController {
 private final NotificationService s;public NotificationController(NotificationService s){this.s=s;}
 @GetMapping public List<Map<String,Object>> list(Authentication a){return s.list(a.getName());}
 @PatchMapping("/{id}/read") public Map<String,Object> read(@PathVariable UUID id,Authentication a){return s.read(id,a.getName());}
 @PatchMapping("/read-all") public Map<String,Object> readAll(Authentication a){return s.readAll(a.getName());}
}
