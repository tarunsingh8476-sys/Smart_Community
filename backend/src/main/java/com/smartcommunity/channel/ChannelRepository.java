package com.smartcommunity.channel;
import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface ChannelRepository extends JpaRepository<Channel,UUID>{List<Channel> findByServerId(UUID id);}
