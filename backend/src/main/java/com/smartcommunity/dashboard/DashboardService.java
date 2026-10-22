package com.smartcommunity.dashboard;
import com.smartcommunity.user.*;import com.smartcommunity.server.*;import com.smartcommunity.membership.*;import com.smartcommunity.conversation.*;import com.smartcommunity.notification.*;
import org.springframework.stereotype.Service;import java.util.*;
@Service
public class DashboardService {
 private final UserService users;private final ServerMemberRepository memberships;private final ConversationParticipantRepository conversations;private final NotificationService notifications;private final ServerService servers;
 public DashboardService(UserService u,ServerMemberRepository m,ConversationParticipantRepository c,NotificationService n,ServerService s){users=u;memberships=m;conversations=c;notifications=n;servers=s;}
 public Map<String,Object> get(String email){return Map.of("profile",users.publicProfile(users.byEmail(email)),"servers",servers.list(email),"conversationCount",conversations.findByUserEmailIgnoreCase(email).size(),"unreadNotificationCount",notifications.unread(email));}
}
