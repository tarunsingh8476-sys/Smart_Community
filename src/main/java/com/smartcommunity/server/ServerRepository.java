package com.smartcommunity.server;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ServerRepository extends JpaRepository<Server,UUID> { List<Server> findByOwnerEmailIgnoreCase(String email); }
