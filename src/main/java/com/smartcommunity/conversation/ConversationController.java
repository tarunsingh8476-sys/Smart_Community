package com.smartcommunity.conversation;
import com.smartcommunity.conversation.dto.*;import jakarta.validation.Valid;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/conversations")
public class ConversationController {
 private final ConversationService s;public ConversationController(ConversationService s){this.s=s;}
 @PostMapping public Map<String,Object> create(Authentication a,@Valid @RequestBody CreateConversationRequest r){return s.create(a.getName(),r);}
 @GetMapping public List<Map<String,Object>> list(Authentication a){return s.list(a.getName());}
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID id,Authentication a){return s.get(id,a.getName());}
 @GetMapping("/{id}/messages") public List<Map<String,Object>> messages(@PathVariable UUID id,Authentication a){return s.messages(id,a.getName());}
 @PostMapping("/{id}/messages") public Map<String,Object> send(@PathVariable UUID id,Authentication a,@Valid @RequestBody ConversationMessageRequest r){return s.send(id,a.getName(),r);}
}
