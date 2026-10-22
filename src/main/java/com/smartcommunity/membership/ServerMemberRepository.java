package com.smartcommunity.membership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ServerMemberRepository extends JpaRepository<ServerMember,UUID> {
 boolean existsByServerIdAndUserEmailIgnoreCase(UUID serverId,String email);
 List<ServerMember> findByUserEmailIgnoreCase(String email);
 List<ServerMember> findByServerId(UUID serverId);
 Optional<ServerMember> findByServerIdAndUserEmailIgnoreCase(UUID id,String email);
 void deleteByServerIdAndUserEmailIgnoreCase(UUID id,String email);
}
