package application.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String userIndex() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String targetURL = "";
        if (role.contains("ROLE_AGENT"))
            targetURL = "/agent/index";
        else if (role.contains("ROLE_CANDIDATE"))
            targetURL = "/candidate/index";
        else if (role.contains("ROLE_ENTERPRISE"))
            targetURL = "/enterprise/index";
        return targetURL;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/agent")
    public String agent(){
        return "/agent/index";
    }
}
