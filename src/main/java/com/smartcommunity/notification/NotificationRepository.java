package com.smartcommunity.notification;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface NotificationRepository extends JpaRepository<Notification,UUID>{List<Notification> findTop100ByRecipientEmailIgnoreCaseOrderByCreatedAtDesc(String email);long countByRecipientEmailIgnoreCaseAndReadFalse(String email);}
