package com.smartcommunity.server;
import com.smartcommunity.server.dto.ServerRequest;
import com.smartcommunity.user.*;
import com.smartcommunity.membership.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class ServerService {
 private final ServerRepository repo;private final UserService users;private final ServerMemberRepository members;
 public ServerService(ServerRepository r,UserService u,ServerMemberRepository m){repo=r;users=u;members=m;}
 @Transactional public Server create(String email,ServerRequest r){Server s=new Server();s.setName(r.name().trim());s.setDescription(r.description());s.setIconUrl(r.iconUrl());s.setOwner(users.byEmail(email));repo.save(s);ServerMember sm=new ServerMember();sm.setServer(s);sm.setUser(s.getOwner());sm.setRole("OWNER");members.save(sm);return s;}
 public List<Map<String,Object>> list(String email){return members.findByUserEmailIgnoreCase(email).stream().map(m->view(m.getServer())).toList();}
 public Map<String,Object> get(UUID id,String email){Server s=repo.findById(id).orElseThrow(()->new NoSuchElementException("Server not found"));if(!members.existsByServerIdAndUserEmailIgnoreCase(id,email))throw new NoSuchElementException("Server not found");return view(s);}
 @Transactional public Map<String,Object> update(UUID id,String email,ServerRequest r){Server s=owned(id,email);s.setName(r.name().trim());s.setDescription(r.description());s.setIconUrl(r.iconUrl());return view(s);}
 @Transactional public void delete(UUID id,String email){repo.delete(owned(id,email));}
 public Server owned(UUID id,String email){Server s=repo.findById(id).orElseThrow(()->new NoSuchElementException("Server not found"));if(!s.getOwner().getEmail().equalsIgnoreCase(email))throw new org.springframework.security.access.AccessDeniedException("Owner only");return s;}
 public Map<String,Object> view(Server s){return Map.of("id",s.getId(),"name",s.getName(),"description",Objects.toString(s.getDescription(),""),"iconUrl",Objects.toString(s.getIconUrl(),""),"ownerId",s.getOwner().getId(),"createdAt",s.getCreatedAt());}
}
