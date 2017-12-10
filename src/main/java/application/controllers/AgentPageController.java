package application.controllers;

import application.model.agent.Agent;
import application.model.agent.service.AgentService;
import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.ApplicantStage;
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
    @ResponseBody
    public void reorderApplications(HttpServletResponse response,
                                    @ModelAttribute(name = "agent") Agent agent,
                                    @RequestParam(value = "prevIndex") int prevIndex,
                                    @RequestParam(value = "newIndex") int newIndex) {
        if (validateApplicationIndexes(agent.getApplications(), prevIndex, newIndex)) {
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
    public String getApplication(@ModelAttribute(name = "agent") Agent agent,
                                 @PathVariable("index") int applicationIndex,
                                 @RequestParam(name = "edit", required = false) String edit,
                                 @RequestParam(name = "quantityError", required = false) String quantityError,
                                 Model model) {
        applicationIndex = applicationIndex - 1;

        if (validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            Enterprise enterprise = agentService.findEnterpriseOfApplication(application);
            model.addAttribute("app", application);
            model.addAttribute("enterprise", enterprise);

            ApplicationRequestParameter parameter = new ApplicationRequestParameter();
            if (edit != null)
                parameter.setEdit(true);
            if (quantityError != null)
                parameter.setEdit(true);
            model.addAttribute("param", parameter);
            model.addAttribute("applicationIndex", applicationIndex);
            return "/agent/application/index";
        } else {
            return "/quantityError/wrong-input";
        }
    }

    @PostMapping("/agent/application/{index}")
    public String saveApplication(@ModelAttribute(name = "agent") Agent agent,
                                  @PathVariable("index") int applicationIndex,
                                  @RequestParam("save") String save,
                                  @RequestParam("profession") String profession,
                                  @RequestParam("quantity") String quantity,
                                  @RequestParam("agentNote") String agentNote) {
        applicationIndex = applicationIndex - 1;
        if (validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
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
            return "redirect:/agent/application/" + (applicationIndex + 1) + isError;
        }
        else
            return "/quantityError/wrong-input";
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
    @ResponseBody
    public void reorderApplicants(HttpServletResponse response,
                                  @ModelAttribute(name = "agent") Agent agent,
                                  @PathVariable("index") int applicationIndex,
                                  @RequestParam("prevIndex") int prevCandidateIndex,
                                  @RequestParam("newIndex") int newCandidateIndex) {
        applicationIndex = applicationIndex - 1;
        if (validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
                        prevCandidateIndex, newCandidateIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            List<Applicant> applicants = application.getApplicants();
            Applicant applicant1 = applicants.remove(prevCandidateIndex);
            applicants.add(newCandidateIndex, applicant1);
            agentService.reorderApplicantsOfApplication(application);
        } else {
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/agent/application/{index}/enterprise")
    public String getEnterpriseOfApplication(@ModelAttribute(name = "agent") Agent agent,
                                             @PathVariable("index") int index, Model model) {
        index = index - 1;
        if (validateApplicationIndexes(agent.getApplications(), index)) {
            Enterprise enterprise = agentService.findEnterpriseOfApplication(agent.getApplications().get(index));
            model.addAttribute("enterprise", enterprise);
            return "enterprise/index";
        } else
            return "error/wrong-input";
    }

    @GetMapping("/agent/application/{index}/applicant/{applicantIndex}")
    public String getApplicantOfApplication(@ModelAttribute(name = "agent") Agent agent,
                                            @PathVariable("index") int applicationIndex,
                                            @PathVariable("applicantIndex") int candidateIndex,
                                            Model model) {
        applicationIndex = applicationIndex - 1;
        candidateIndex = candidateIndex - 1;

        if (validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
                        candidateIndex)) {
            Applicant applicant = agent.getApplications().get(applicationIndex).getApplicants().get(candidateIndex);
            model.addAttribute("candidate", applicant);
            return "candidate/index";
        } else
            return "error/wrong-input";
    }

    @PostMapping("agent/application/{index}/applicant/{applicantIndex}/newStage")
    @ResponseBody
    public void changeApplicantStage(HttpServletResponse response,
                                     @ModelAttribute("agent") Agent agent,
                                     @PathVariable("index") int applicationIndex,
                                     @PathVariable("applicantIndex") int candidateIndex,
                                     @RequestParam("stage") String stage) {
        applicationIndex = applicationIndex - 1;
        candidateIndex = candidateIndex - 1;

        if (validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
                        candidateIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            Applicant applicant = application.getApplicants().get(candidateIndex);
            applicant.setApplicantStage(ApplicantStage.valueOf(stage));
            agentService.updateApplicantOfApplicationStage(application, applicant);
        }
        else
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private boolean validateApplicationIndexes(List<Application> applications, int appIndex) {
        return 0 <= appIndex && appIndex < applications.size();
    }

    private boolean validateApplicationIndexes(List<Application> applications, int prevAppIndex, int newAppIndex) {
        return 0 <= prevAppIndex && prevAppIndex < applications.size() &&
                0 <= newAppIndex && newAppIndex < applications.size();
    }

    private boolean validateApplicantsIndexes(List<Applicant> applicants,
                                    int prevApplicantIndex, int newApplicantIndex) {
        return 0 <= prevApplicantIndex && prevApplicantIndex < applicants.size() &&
                0 <= newApplicantIndex && newApplicantIndex < applicants.size();
    }

    private boolean validateApplicantsIndexes(List<Applicant> applicants,
                              int applicantIndex) {
        return 0 <= applicantIndex && applicantIndex < applicants.size();
    }

    @PostMapping("/agent/application/{index}/agentCollapse")
    @ResponseBody
    public void collapseApplication(HttpServletResponse response,
                                @ModelAttribute(name = "agent") Agent agent,
                                @PathVariable("index") int applicationIndex,
                                @RequestParam("collapsed") boolean collapsed) {
        applicationIndex = applicationIndex - 1;

        if (validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            application.setAgentCollapsed(collapsed);
            agentService.updateApplicationCollapsed(application);
        }
        else
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @PostMapping("/agent/application/{index}/agentCollapseApplicants")
    @ResponseBody
    public void collapseApplicants(HttpServletResponse response,
                                    @ModelAttribute(name = "agent") Agent agent,
                                    @PathVariable("index") int applicationIndex,
                                    @RequestParam("collapsed") boolean collapsed) {
        applicationIndex = applicationIndex - 1;
        if (validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            application.setAgentCollapsedApplicants(collapsed);
            agentService.updateApplicationCollapsedApplicants(application);
        }
        else
            try {
                response.sendRedirect("/error/wrong-input");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
