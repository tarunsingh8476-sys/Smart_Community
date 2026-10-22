package com.smartcommunity.channel;
import com.smartcommunity.channel.dto.ChannelRequest;import jakarta.validation.Valid;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/servers/{serverId}/channels")
public class ChannelController {
 private final ChannelService s;public ChannelController(ChannelService s){this.s=s;}
 @PostMapping public Map<String,Object> create(@PathVariable UUID serverId,Authentication a,@Valid @RequestBody ChannelRequest r){return s.create(serverId,a.getName(),r);}
 @GetMapping public List<Map<String,Object>> list(@PathVariable UUID serverId,Authentication a){return s.list(serverId,a.getName());}
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID serverId,@PathVariable UUID id,Authentication a){return s.get(id,a.getName());}
 @PutMapping("/{id}") public Map<String,Object> update(@PathVariable UUID serverId,@PathVariable UUID id,Authentication a,@Valid @RequestBody ChannelRequest r){return s.update(id,a.getName(),r);}
 @DeleteMapping("/{id}") public void delete(@PathVariable UUID serverId,@PathVariable UUID id,Authentication a){s.delete(id,a.getName());}
}
