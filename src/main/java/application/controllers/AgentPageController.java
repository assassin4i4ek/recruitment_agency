package application.controllers;

import application.model.agent.Agent;
import application.model.agent.service.AgentService;
import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.service.CandidateService;
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
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes("agent")
public class AgentPageController {
    @Autowired
    private AgentService agentService;

    @ModelAttribute("agent")
    private Agent agent(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return agentService.findAgentById(user.getId());
    }

    @GetMapping("/agent")
    public String agent() {
        return "/agent/index";
    }

    @PostMapping("/agent/reorderApplications")
    public void reorderApplications(HttpServletResponse response,
                                    @ModelAttribute(name="agent") Agent agent,
                                    @RequestParam(value="prevIndex") int prevIndex,
                                    @RequestParam(value="newIndex") int newIndex) {
        if (0 <= prevIndex && prevIndex < agent.getApplications().size() &&
                0 <= newIndex && newIndex < agent.getApplications().size()) {
            Application app1 = agent.getApplications().remove(prevIndex);
            agent.getApplications().add(newIndex, app1);
            agentService.reorderApplicationsOfAgent(agent);
        } else {
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/agent/application/{index}")
    public String getApplication(@ModelAttribute(name="agent") Agent agent,
                                 @PathVariable("index") int index,
                                 @RequestParam(name = "edit", required = false) String edit,
                                 @RequestParam(name = "quantityError", required = false) String quantityError,
                                 Model model) {
        index = index - 1;

        if (0 <= index && index < agent.getApplications().size()) {
            Application application = agent.getApplications().get(index);
            Enterprise enterprise = agentService.findEnterpriseOfApplication(application);
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
    public String saveApplication(@ModelAttribute(name="agent") Agent agent,
                                  @PathVariable("index") int index,
                                  @RequestParam("save") String save,
                                  @RequestParam("profession") String profession,
                                  @RequestParam("quantity") String quantity,
                                  @RequestParam("agentNote") String agentNote) {
        index = index - 1;
        Application application = agent.getApplications().get(index);
        application.setProfession(profession);
        String isError = "";
        try {
            short shortQuantity = Short.parseShort(quantity);
            application.setQuantity(shortQuantity);
        } catch (NumberFormatException e) {
            isError = "?edit&quantityError";
        }
        application.setAgentNote(agentNote);
        agentService.updateApplicationInfo(application);
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

    @PostMapping("/agent/application/{index}/reorderApplicants")
    public void reorderApplicants(HttpServletResponse response,
                                  @ModelAttribute(name = "agent") Agent agent,
                                  @PathVariable("index") int index,
                                  @RequestParam("prevIndex") int prevIndex,
                                  @RequestParam("newIndex") int newIndex) {
        index = index - 1;
        if (0 <= index && index < agent.getApplications().size() &&
                0 <= prevIndex && prevIndex < agent.getApplications().size() &&
                0 <= newIndex && index < agent.getApplications().size()) {

            Application application = agent.getApplications().get(index);
            List<Applicant> applicants = application.getApplicants();
            Applicant applicant1 = applicants.remove(prevIndex);
            applicants.add(newIndex, applicant1);
            agentService.reorderApplicantsOfApplication(application);
        }
        else {
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/agent/application/{index}/enterprise")
    public String getEnterpriseOfApplication(@ModelAttribute(name="agent") Agent agent,
                                 @PathVariable("index") int index, Model model) {
        index = index - 1;
        if (0 <= index && index < agent.getApplications().size()) {
            Enterprise enterprise = agentService.findEnterpriseOfApplication(agent.getApplications().get(index));
            model.addAttribute(enterprise);
            return "enterprise/index";
        }
        else
            return "error/wrong-input";
    }
}
