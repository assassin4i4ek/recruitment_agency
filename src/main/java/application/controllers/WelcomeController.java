package application.controllers;

import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        String role = authentication.getAuthorities().toString();
        String targetURL = "/";
        if (role.contains("ROLE_AGENT"))
            targetURL = "/agent";
        else if (role.contains("ROLE_CANDIDATE"))
            targetURL = "/candidate";
        else if (role.contains("ROLE_ENTERPRISE"))
            targetURL = "/enterprise";
        return "redirect:" + targetURL;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/agent")
    public String agent(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getId());
        return "/agent/index";
    }

    @GetMapping("/candidate")
    public String candidate() {
        return "/candidate/index";
    }

    @GetMapping("/enterprise")
    public String enterprise() {
        return "/enterprise/index";
    }

    @GetMapping("/error/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
