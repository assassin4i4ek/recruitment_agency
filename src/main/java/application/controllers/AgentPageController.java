package application.controllers;

import application.model.application.Application;
import application.model.enterprise.Enterprise;
import application.model.enterprise.serivice.EnterpriseService;
import application.model.user.User;
import application.model.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@SessionAttributes("applications")
public class AgentPageController {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EnterpriseService enterpriseService;

    @ModelAttribute("applications")
    private List<Application> applications(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return applicationService.findApplicationsByAgentId(user.getId());
    }

    @GetMapping("/agent")
    public String agent() {
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

    @GetMapping("/agent/application/{index}")
    public String getApplication(@ModelAttribute(name="applications") List<Application> applications,
                                 @PathVariable("index") int index,
                                 @RequestParam(name = "edit", required = false) String edit,
                                 @RequestParam(name = "quantityError", required = false) String quantityError,
                                 Model model) {
        index = index - 1;

        if (0 <= index && index < applications.size()) {
            Application application = applications.get(index);
            Enterprise enterprise = enterpriseService.findEnterpriseById(application.getEnterpriseId());
            model.addAttribute("app", application);
            model.addAttribute("enterprise", enterprise);

            ApplicationRequestParameter parameter = new ApplicationRequestParameter();
            if (edit != null)
                parameter.setEdit(true);
            if (quantityError != null)
                parameter.setEdit(true);
            model.addAttribute("param", parameter);

            return "/agent/application/index";
        }
        else {
            return "/quantityError/wrong-input";
        }
    }

    @PostMapping("/agent/application/{index}")
    public String saveApplication(@ModelAttribute(name="applications") List<Application> applications,
                                  @PathVariable("index") int index,
                                  @RequestParam("save") String save,
                                  @RequestParam("profession") String profession,
                                  @RequestParam("quantity") String quantity,
                                  @RequestParam("agentNote") String agentNote) {
        index = index - 1;
        Application application = applications.get(index);
        application.setProfession(profession);
        String isError = "";
        try {
            short shortQuantity = Short.parseShort(quantity);
            application.setQuantity(shortQuantity);
        } catch (NumberFormatException e) {
            isError = "?edit&quantityError";
        }
        application.setAgentNote(agentNote);
        applicationService.updateApplicationInfo(application);
        return "redirect:/agent/application/" + (index + 1) + isError;
    }

    private class ApplicationRequestParameter {
        private boolean edit = false;
        private boolean quantityError = false;

        public boolean isEdit() {
            return edit;
        }

        public void setEdit(boolean edit) {
            this.edit = edit;
        }

        public boolean isQuantityError() {
            return quantityError;
        }

        public void setQuantityError(boolean quantityError) {
            this.quantityError = quantityError;
        }
    }
}
