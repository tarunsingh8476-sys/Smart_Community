package com.smartcommunity.channel;
import com.smartcommunity.channel.dto.ChannelRequest;import com.smartcommunity.membership.*;import com.smartcommunity.server.*;
import org.springframework.stereotype.Service;import java.util.*;
@Service
public class ChannelService {
 private final ChannelRepository channels;private final ServerService servers;private final ServerMemberRepository members;
 public ChannelService(ChannelRepository c,ServerService s,ServerMemberRepository m){channels=c;servers=s;members=m;}
 public Map<String,Object> create(UUID sid,String email,ChannelRequest r){Server s=servers.owned(sid,email);Channel c=new Channel();c.setServer(s);c.setName(r.name().trim());c.setDescription(r.description());return view(channels.save(c));}
 public List<Map<String,Object>> list(UUID sid,String email){requireMember(sid,email);return channels.findByServerId(sid).stream().map(this::view).toList();}
 public Map<String,Object> get(UUID id,String email){Channel c=channels.findById(id).orElseThrow(()->new NoSuchElementException("Channel not found"));requireMember(c.getServer().getId(),email);return view(c);}
 public Map<String,Object> update(UUID id,String email,ChannelRequest r){Channel c=channels.findById(id).orElseThrow(()->new NoSuchElementException("Channel not found"));servers.owned(c.getServer().getId(),email);c.setName(r.name().trim());c.setDescription(r.description());return view(c);}
 public void delete(UUID id,String email){Channel c=channels.findById(id).orElseThrow(()->new NoSuchElementException("Channel not found"));servers.owned(c.getServer().getId(),email);channels.delete(c);}
 public void requireMember(UUID id,String email){if(!members.existsByServerIdAndUserEmailIgnoreCase(id,email))throw new org.springframework.security.access.AccessDeniedException("Server membership required");}
 public Map<String,Object> view(Channel c){return Map.of("id",c.getId(),"name",c.getName(),"description",Objects.toString(c.getDescription(),""),"serverId",c.getServer().getId(),"createdAt",c.getCreatedAt());}
}
