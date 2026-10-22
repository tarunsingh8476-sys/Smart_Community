package com.smartcommunity.user;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/users")
public class UserController {
 private final UserService users;
 public UserController(UserService users){this.users=users;}
 @GetMapping("/me") public Map<String,Object> me(Authentication a){return users.publicProfile(users.byEmail(a.getName()));}
}
