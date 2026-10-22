package com.smartcommunity.notification;
import com.smartcommunity.user.*;import org.springframework.stereotype.Service;import java.util.*;
@Service
public class NotificationService {
 private final NotificationRepository repo;private final UserService users;
 public NotificationService(NotificationRepository r,UserService u){repo=r;users=u;}
 public List<Map<String,Object>> list(String email){return repo.findTop100ByRecipientEmailIgnoreCaseOrderByCreatedAtDesc(email).stream().map(this::view).toList();}
 public Map<String,Object> create(String email,String type,String description){Notification n=new Notification();n.setRecipient(users.byEmail(email));n.setType(type);n.setDescription(description);return view(repo.save(n));}
 public Map<String,Object> read(UUID id,String email){Notification n=repo.findById(id).orElseThrow(()->new NoSuchElementException("Notification not found"));if(!n.getRecipient().getEmail().equalsIgnoreCase(email))throw new org.springframework.security.access.AccessDeniedException("Own notifications only");n.setRead(true);return view(n);}
 public Map<String,Object> readAll(String email){repo.findTop100ByRecipientEmailIgnoreCaseOrderByCreatedAtDesc(email).forEach(n->n.setRead(true));return Map.of("message","Notifications marked as read");}
 public long unread(String email){return repo.countByRecipientEmailIgnoreCaseAndReadFalse(email);}
 public Map<String,Object> view(Notification n){return Map.of("id",n.getId(),"type",n.getType(),"description",n.getDescription(),"read",n.isRead(),"createdAt",n.getCreatedAt());}
}
