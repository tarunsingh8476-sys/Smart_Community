package com.smartcommunity.membership;
import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/servers/{serverId}/members")
public class ServerMemberController {
 private final ServerMemberService service;public ServerMemberController(ServerMemberService s){service=s;}
 @PostMapping public Map<String,Object> join(@PathVariable UUID serverId,Authentication a){return service.join(serverId,a.getName());}
 @DeleteMapping("/me") public Map<String,Object> leave(@PathVariable UUID serverId,Authentication a){return service.leave(serverId,a.getName());}
 @GetMapping public List<Map<String,Object>> list(@PathVariable UUID serverId,Authentication a){return service.list(serverId,a.getName());}
}
