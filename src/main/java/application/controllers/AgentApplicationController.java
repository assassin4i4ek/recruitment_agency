package application.controllers;

import application.model.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("application")
public class AgentApplicationController {


    @GetMapping("/agent/application")
    public String application(@ModelAttribute(name="application") Application application,
                              Model model) {

        return "/agent/application/index";
    }
}
