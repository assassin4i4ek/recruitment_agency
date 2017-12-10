package application.controllers;

import application.controllers.parameters.EnterpriseRequestParameter;
import application.model.enterprise.Enterprise;
import application.model.enterprise.serivice.EnterpriseService;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String enterprise(@ModelAttribute("enterprise") Enterprise enterprise,
                             @RequestParam(name = "edit", required = false) String edit,
                             Model model) {
        EnterpriseRequestParameter parameter = new EnterpriseRequestParameter();
        if (edit != null)
            parameter.setEdit(true);
        model.addAttribute("param", parameter);
        return "/enterprise/index";
    }

    @PostMapping("/enterprise")
    public String saveEnterprise(@ModelAttribute("enterprise") Enterprise enterprise,
                                 @RequestParam("save") String save,
                                 @RequestParam("name") String name,
                                 @RequestParam("email") String email) {
        enterprise.setName(name);
        enterprise.setEmail(email);
        enterpriseService.updateEnterpriseInfo(enterprise);
        return "redirect:/enterprise";
    }
}
