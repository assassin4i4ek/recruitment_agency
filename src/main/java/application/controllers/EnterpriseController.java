package application.controllers;

import application.controllers.parameters.ApplicationRequestParameter;
import application.controllers.parameters.EnterpriseRequestParameter;
import application.model.agent.Agent;
import application.model.application.Application;
import application.model.enterprise.Enterprise;
import application.model.enterprise.serivice.EnterpriseService;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@SessionAttributes("enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private IndexValidator indexValidator;

    @ModelAttribute("enterprise")
    private Enterprise enterprise(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return enterpriseService.findEnterpriseById(user.getId());
    }

    @GetMapping("/enterprise")
    public String enterprise(@ModelAttribute("enterprise") Enterprise enterprise,
                             @RequestParam(name = "edit", required = false) String edit,
                             Model model) {
        enterpriseService.checkForUpdates(enterprise);
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

    @PostMapping("/enterprise/reorderApplications")
    @ResponseBody
    public void reorderApplications(HttpServletResponse response,
                                    @ModelAttribute(name = "enterprise") Enterprise enterprise,
                                    @RequestParam(value = "prevIndex") int prevIndex,
                                    @RequestParam(value = "newIndex") int newIndex) {
        if (indexValidator.validateApplicationIndexes(enterprise.getApplications(), prevIndex, newIndex)) {
            Application app1 = enterprise.getApplications().remove(prevIndex);
            enterprise.getApplications().add(newIndex, app1);
            enterpriseService.reorderApplicationsOfEnterprise(enterprise);
        } else {
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/enterprise/application/{index}")
    public String getApplication(@ModelAttribute(name = "enterprise") Enterprise enterprise,
                                 @PathVariable("index") int applicationIndex,
                                 @RequestParam("edit") String edit,
                                 @RequestParam(name = "quantityError", required = false) String quantityError,
                                 @RequestParam(name = "professionError", required = false) String professionError,
                                 Model model) {
        applicationIndex = applicationIndex - 1;

        if (indexValidator.validateApplicationIndexes(enterprise.getApplications(), applicationIndex)) {
            Application application = enterprise.getApplications().get(applicationIndex);
            model.addAttribute("app", application);

            ApplicationRequestParameter parameter = new ApplicationRequestParameter();
            if (edit != null)
                parameter.setEdit(true);
            if (professionError != null)
                parameter.setProfessionError(true);
            if (quantityError != null)
                parameter.setQuantityError(true);

            model.addAttribute("param", parameter);
            model.addAttribute("applicationIndex", applicationIndex);
            return "/enterprise/application/index";
        } else {
            return "/error/wrong-input";
        }
    }

    @PostMapping("/enterprise/application/{index}")
    public String saveApplication(@ModelAttribute(name = "enterprise") Enterprise enterprise,
                                  @PathVariable("index") int applicationIndex,
                                  @RequestParam("save") String save,
                                  @RequestParam("profession") String profession,
                                  @RequestParam("quantity") String quantity) {
        applicationIndex = applicationIndex - 1;
        if (indexValidator.validateApplicationIndexes(enterprise.getApplications(), applicationIndex)) {
            Application application = enterprise.getApplications().get(applicationIndex);
            String isError = "";
            if (enterpriseService.validateQuantity(quantity)) {
                application.setQuantity(Short.parseShort(quantity));
            }
            else {
                isError += "&quantityError";
            }
            if (enterpriseService.validateProfession(profession)) {
                application.setProfession(profession);
            }
            else {
                isError += "&professionError";
            }
            if (isError.isEmpty()) {
                enterpriseService.updateEnterpriseApplicationInfo(application);
                return "/enterprise/index";
            }
            else {
                isError = "?edit" + isError;
                return "redirect:/enterprise/application/" + (applicationIndex + 1) + isError;
            }
        }
        else
            return "/error/wrong-input";
    }

    @PostMapping("enterprise/application/{index}/enterpriseCollapse")
    @ResponseBody
    public void collapseApplication(HttpServletResponse response,
                                    @ModelAttribute(name = "enterprise") Enterprise enterprise,
                                    @PathVariable("index") int applicationIndex,
                                    @RequestParam("collapsed") boolean collapsed) {
        applicationIndex = applicationIndex - 1;

        if (indexValidator.validateApplicationIndexes(enterprise.getApplications(), applicationIndex)) {
            Application application = enterprise.getApplications().get(applicationIndex);
            application.setEnterpriseCollapsed(collapsed);
            enterpriseService.updateApplicationCollapsed(application);
        }
        else
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
