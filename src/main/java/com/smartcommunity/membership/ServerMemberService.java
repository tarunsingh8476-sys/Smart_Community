package com.smartcommunity.membership;
import com.smartcommunity.server.*;
import com.smartcommunity.user.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ServerMemberService {
 private final ServerRepository servers;private final ServerMemberRepository members;private final UserService users;
 public ServerMemberService(ServerRepository s,ServerMemberRepository m,UserService u){servers=s;members=m;users=u;}
 public Map<String,Object> join(UUID id,String email){Server s=servers.findById(id).orElseThrow(()->new NoSuchElementException("Server not found"));if(!members.existsByServerIdAndUserEmailIgnoreCase(id,email)){ServerMember m=new ServerMember();m.setServer(s);m.setUser(users.byEmail(email));members.save(m);}return Map.of("message","Joined server");}
 public Map<String,Object> leave(UUID id,String email){ServerMember m=members.findByServerIdAndUserEmailIgnoreCase(id,email).orElseThrow(()->new NoSuchElementException("Membership not found"));if(m.getRole().equals("OWNER"))throw new IllegalArgumentException("Server owner cannot leave; transfer ownership or delete the server");members.delete(m);return Map.of("message","Left server");}
 public List<Map<String,Object>> list(UUID id,String email){if(!members.existsByServerIdAndUserEmailIgnoreCase(id,email))throw new org.springframework.security.access.AccessDeniedException("Server membership required");return members.findByServerId(id).stream().map(m->Map.<String,Object>of("userId",m.getUser().getId(),"name",m.getUser().getName(),"email",m.getUser().getEmail(),"role",m.getRole())).toList();}
}
