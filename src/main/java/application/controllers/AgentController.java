package application.controllers;

import application.model.application.Application;
import application.model.user.User;
import application.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("applications")
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

    @PostMapping("/agent/reorderApplications")
    public void reorderApplications(HttpServletResponse response,
                                    @ModelAttribute(name="applications") List<Application> applications,
                                    @RequestParam(value="prevIndex") int prevIndex,
                                    @RequestParam(value="newIndex") int newIndex) {
        Application app1 = applications.remove(prevIndex);
        applications.add(newIndex, app1);
        applicationService.reorderApplicationsOfAgent(applications);
    }
}
