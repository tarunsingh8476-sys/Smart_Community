package com.smartcommunity.message;
import com.smartcommunity.message.dto.MessageRequest;import jakarta.validation.Valid;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/channels/{channelId}/messages")
public class MessageController {
 private final MessageService s;public MessageController(MessageService s){this.s=s;}
 @PostMapping public Map<String,Object> send(@PathVariable UUID channelId,Authentication a,@Valid @RequestBody MessageRequest r){return s.send(channelId,a.getName(),r);}
 @GetMapping public Map<String,Object> list(@PathVariable UUID channelId,Authentication a,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="30") int size){return s.list(channelId,a.getName(),page,size);}
 @PutMapping("/{id}") public Map<String,Object> edit(@PathVariable UUID channelId,@PathVariable UUID id,Authentication a,@Valid @RequestBody MessageRequest r){return s.edit(id,a.getName(),r);}
 @DeleteMapping("/{id}") public void delete(@PathVariable UUID channelId,@PathVariable UUID id,Authentication a){s.delete(id,a.getName());}
}
