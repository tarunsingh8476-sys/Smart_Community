package com.smartcommunity.dashboard;
import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
 private final DashboardService s;public DashboardController(DashboardService s){this.s=s;}
 @GetMapping public Map<String,Object> dashboard(Authentication a){return s.get(a.getName());}
}
