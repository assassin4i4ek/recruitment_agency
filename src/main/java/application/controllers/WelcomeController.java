package application.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        String role = authentication.getAuthorities().toString();
        String targetURL;
        if (role.contains("ROLE_AGENT"))
            targetURL = "/agent";
        else if (role.contains("ROLE_CANDIDATE"))
            targetURL = "/candidate";
        else if (role.contains("ROLE_ENTERPRISE"))
            targetURL = "/enterprise";
        else
            targetURL = "/error/access-denied";
        return "redirect:" + targetURL;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @GetMapping("/error/wrong-input")
    public String wrongInput() {
        return "/error/wrong-input";
    }
}
