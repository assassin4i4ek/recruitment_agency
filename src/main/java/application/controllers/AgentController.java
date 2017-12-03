package application.controllers;

import application.model.application.Application;
import application.model.user.User;
import application.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AgentController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/agent")
    public String agent(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        List<Application> applications = applicationService.findApplicationsByAgentId(user.getId());
        model.addAttribute("applications", applications);
        return "/agent/index";
    }
}
