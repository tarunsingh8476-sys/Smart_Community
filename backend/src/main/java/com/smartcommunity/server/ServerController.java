package com.smartcommunity.server;
import com.smartcommunity.server.dto.ServerRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/servers")
public class ServerController {
 private final ServerService s;public ServerController(ServerService s){this.s=s;}
 @PostMapping public Map<String,Object> create(Authentication a,@Valid @RequestBody ServerRequest r){return s.view(s.create(a.getName(),r));}
 @GetMapping public List<Map<String,Object>> list(Authentication a){return s.list(a.getName());}
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID id,Authentication a){return s.get(id,a.getName());}
 @PutMapping("/{id}") public Map<String,Object> update(@PathVariable UUID id,Authentication a,@Valid @RequestBody ServerRequest r){return s.update(id,a.getName(),r);}
 @DeleteMapping("/{id}") public void delete(@PathVariable UUID id,Authentication a){s.delete(id,a.getName());}
}
