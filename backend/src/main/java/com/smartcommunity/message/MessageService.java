package com.smartcommunity.message;
import com.smartcommunity.channel.*;import com.smartcommunity.message.dto.MessageRequest;import com.smartcommunity.user.*;
import org.springframework.data.domain.*;import org.springframework.stereotype.Service;import java.util.*;
@Service
public class MessageService {
 private final MessageRepository repo;private final ChannelService channels;private final UserService users;
 public MessageService(MessageRepository r,ChannelService c,UserService u){repo=r;channels=c;users=u;}
 public Map<String,Object> send(UUID channelId,String email,MessageRequest r){Channel c= (Channel) channels.get(channelId,email);Message m=new Message();m.setChannel(c);m.setSender(users.byEmail(email));m.setContent(r.content().trim());return view(repo.save(m));}
 public Map<String,Object> list(UUID id,String email,int page,int size){channels.get(id,email);Page<Message> p=repo.findByChannelIdOrderByCreatedAtDesc(id,PageRequest.of(Math.max(page,0),Math.min(Math.max(size,1),100)));return Map.of("items",p.getContent().stream().map(this::view).toList(),"page",p.getNumber(),"size",p.getSize(),"totalElements",p.getTotalElements(),"totalPages",p.getTotalPages());}
 public Map<String,Object> edit(UUID id,String email,MessageRequest r){Message m=repo.findById(id).orElseThrow(()->new NoSuchElementException("Message not found"));if(!m.getSender().getEmail().equalsIgnoreCase(email))throw new org.springframework.security.access.AccessDeniedException("Own messages only");channels.get(m.getChannel().getId(),email);m.setContent(r.content().trim());return view(m);}
 public void delete(UUID id,String email){Message m=repo.findById(id).orElseThrow(()->new NoSuchElementException("Message not found"));if(!m.getSender().getEmail().equalsIgnoreCase(email))throw new org.springframework.security.access.AccessDeniedException("Own messages only");channels.get(m.getChannel().getId(),email);repo.delete(m);}
 public Map<String,Object> view(Message m){return Map.of("id",m.getId(),"content",m.getContent(),"senderId",m.getSender().getId(),"senderName",m.getSender().getName(),"channelId",m.getChannel().getId(),"createdAt",m.getCreatedAt(),"updatedAt",m.getUpdatedAt());}
}
