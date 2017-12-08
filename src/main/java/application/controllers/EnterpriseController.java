package application.controllers;

import application.model.enterprise.Enterprise;
import application.model.enterprise.serivice.EnterpriseService;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @ModelAttribute("enterprise")
    private Enterprise enterprise(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return enterpriseService.findEnterpriseById(user.getId());
    }

    @GetMapping("/enterprise")
    public String enterprise() {
        return "/enterprise/index";
    }

}
