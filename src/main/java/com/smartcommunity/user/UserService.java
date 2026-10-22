package com.smartcommunity.user;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class UserService {
 private final UserRepository repo;
 public UserService(UserRepository repo){this.repo=repo;}
 public User require(UUID id){return repo.findById(id).orElseThrow(()->new NoSuchElementException("User not found"));}
 public User byEmail(String email){return repo.findByEmailIgnoreCase(email).orElseThrow(()->new NoSuchElementException("User not found"));}
 public Map<String,Object> publicProfile(User u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"avatarUrl",u.getAvatarUrl()==null?"":u.getAvatarUrl(),"emailVerified",u.isEmailVerified());}
}
