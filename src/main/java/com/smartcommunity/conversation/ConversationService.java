package com.smartcommunity.conversation;
import com.smartcommunity.conversation.dto.*;import com.smartcommunity.user.*;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.util.*;
@Service
public class ConversationService {
 private final ConversationRepository conversations;private final ConversationParticipantRepository participants;private final ConversationMessageRepository messages;private final UserService users;
 public ConversationService(ConversationRepository c,ConversationParticipantRepository p,ConversationMessageRepository m,UserService u){conversations=c;participants=p;messages=m;users=u;}
 @Transactional public Map<String,Object> create(String email,CreateConversationRequest r){
  User creator=users.byEmail(email);Set<String> emails=new LinkedHashSet<>();emails.add(email.toLowerCase(Locale.ROOT));r.participantEmails().forEach(x->emails.add(x.toLowerCase(Locale.ROOT)));
  if(!r.groupConversation()&&emails.size()>2)throw new IllegalArgumentException("Direct conversations can have only two participants");
  if(!r.groupConversation()&&emails.size()!=2)throw new IllegalArgumentException("Add another participant for a direct conversation");
  Conversation c=new Conversation();c.setCreatedBy(creator);c.setGroupConversation(r.groupConversation());c.setTitle((r.title()==null||r.title().isBlank())?(r.groupConversation()?"Group conversation":"Direct conversation"):r.title().trim());conversations.save(c);
  for(String e:emails){User u=users.byEmail(e);ConversationParticipant p=new ConversationParticipant();p.setConversation(c);p.setUser(u);participants.save(p);}
  return view(c,email);
 }
 public List<Map<String,Object>> list(String email){return participants.findByUserEmailIgnoreCase(email).stream().map(p->view(p.getConversation(),email)).toList();}
 public Map<String,Object> get(UUID id,String email){Conversation c=require(id,email);return view(c,email);}
 public Map<String,Object> send(UUID id,String email,ConversationMessageRequest r){Conversation c=require(id,email);ConversationMessage m=new ConversationMessage();m.setConversation(c);m.setSender(users.byEmail(email));m.setContent(r.content().trim());messages.save(m);return messageView(m);}
 public List<Map<String,Object>> messages(UUID id,String email){require(id,email);return messages.findTop50ByConversationIdOrderByCreatedAtDesc(id).stream().map(this::messageView).toList();}
 private Conversation require(UUID id,String email){Conversation c=conversations.findById(id).orElseThrow(()->new NoSuchElementException("Conversation not found"));if(!participants.existsByConversationIdAndUserEmailIgnoreCase(id,email))throw new NoSuchElementException("Conversation not found");return c;}
 private Map<String,Object> view(Conversation c,String email){return Map.of("id",c.getId(),"title",c.getTitle(),"groupConversation",c.isGroupConversation(),"createdBy",c.getCreatedBy().getId(),"participants",participants.findByConversationId(c.getId()).stream().map(p->Map.<String,Object>of("id",p.getUser().getId(),"name",p.getUser().getName(),"email",p.getUser().getEmail())).toList(),"createdAt",c.getCreatedAt());}
 private Map<String,Object> messageView(ConversationMessage m){return Map.of("id",m.getId(),"content",m.getContent(),"conversationId",m.getConversation().getId(),"senderId",m.getSender().getId(),"senderName",m.getSender().getName(),"createdAt",m.getCreatedAt());}
}
