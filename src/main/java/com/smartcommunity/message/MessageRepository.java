package com.smartcommunity.message;
import org.springframework.data.domain.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface MessageRepository extends JpaRepository<Message,UUID>{Page<Message> findByChannelIdOrderByCreatedAtDesc(UUID channelId,Pageable p);}
