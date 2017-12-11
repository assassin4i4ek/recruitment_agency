package application.controllers;

import application.controllers.parameters.ApplicationRequestParameter;
import application.controllers.parameters.CandidateRequestParameter;
import application.controllers.parameters.EnterpriseRequestParameter;
import application.model.agent.Agent;
import application.model.agent.service.AgentService;
import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.ApplicantStage;
import application.model.candidate.Candidate;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("agent")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @Autowired
    private IndexValidator indexValidator;

    @ModelAttribute("agent")
    private Agent agent(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return agentService.findAgentById(user.getId());
    }

    @GetMapping("/agent")
    public String agent(@ModelAttribute(name = "agent") Agent agent) {
        agentService.checkForUpdates(agent);
        return "/agent/index";
    }

    @PostMapping("/agent/reorderApplications")
    @ResponseBody
    public void reorderApplications(HttpServletResponse response,
                                    @ModelAttribute(name = "agent") Agent agent,
                                    @RequestParam(value = "prevIndex") int prevIndex,
                                    @RequestParam(value = "newIndex") int newIndex) {
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), prevIndex, newIndex)) {
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
                                 @RequestParam(name = "professionError", required = false) String professionError,
                                 @RequestParam(name = "finalizeError", required = false) String finalizeError,
                                 Model model) {
        applicationIndex = applicationIndex - 1;

        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            model.addAttribute("app", application);

            ApplicationRequestParameter parameter = new ApplicationRequestParameter();
            if (edit != null)
                parameter.setEdit(true);
            if (quantityError != null)
                parameter.setQuantityError(true);
            if (finalizeError != null)
                parameter.setFinalizeError(true);
            if (professionError != null)
                parameter.setProfessionError(true);
            model.addAttribute("param", parameter);
            model.addAttribute("applicationIndex", applicationIndex);
            return "/agent/application/index";
        } else {
            return "/error/wrong-input";
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
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            String isError = "";
            try {
                short shortQuantity = Short.parseShort(quantity);
                application.setQuantity(shortQuantity);
            } catch (NumberFormatException e) {
                isError = "?edit&quantityError";
            }
            if (agentService.validateProfession(profession)) {
                application.setProfession(profession);
            }
            else {
                isError += "&professionError";
            }
            application.setAgentNote(agentNote);
            agentService.updateApplicationInfo(application);
            return "redirect:/agent/application/" + (applicationIndex + 1) + isError;
        }
        else
            return "/error/wrong-input";
    }

    @PostMapping("/agent/application/{index}/reorderApplicants")
    @ResponseBody
    public void reorderApplicants(HttpServletResponse response,
                                  @ModelAttribute(name = "agent") Agent agent,
                                  @PathVariable("index") int applicationIndex,
                                  @RequestParam("prevIndex") int prevCandidateIndex,
                                  @RequestParam("newIndex") int newCandidateIndex) {
        applicationIndex = applicationIndex - 1;
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                indexValidator.validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
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
                                             @PathVariable("index") int applicationIndex,
                                             @RequestParam(name = "edit", required = false) String edit,
                                             Model model) {
        applicationIndex = applicationIndex - 1;
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Enterprise enterprise = agent.getApplications().get(applicationIndex).getEnterprise();
            model.addAttribute("enterprise", enterprise);

            EnterpriseRequestParameter parameter = new EnterpriseRequestParameter();
            if (edit != null) {
                parameter.setEdit(true);
            }
            model.addAttribute("param", parameter);
            return "agent/enterprise/index";
        } else
            return "error/wrong-input";
    }

    @PostMapping("/agent/application/{index}/enterprise")
    public String saveEnterpriseOfApplication(@ModelAttribute(name = "agent") Agent agent,
                                             @PathVariable("index") int applicationIndex,
                                             @RequestParam("save") String save,
                                             @RequestParam("name") String name,
                                             @RequestParam("email") String email,
                                             @RequestParam("contactPersonName") String contactPersonName,
                                             Model model) {
        applicationIndex = applicationIndex - 1;
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Enterprise enterprise = agent.getApplications().get(applicationIndex).getEnterprise();
            enterprise.setName(name);
            enterprise.setEmail(email);
            enterprise.setContactPersonName(contactPersonName);
            agentService.updateEnterpriseInfo(enterprise);
            return "redirect:/agent/application/" + (applicationIndex + 1) + "/enterprise";
        } else
            return "error/wrong-input";
    }

    @GetMapping("/agent/application/{index}/applicant/{applicantIndex}")
    public String getApplicantOfApplication(@ModelAttribute(name = "agent") Agent agent,
                                            @PathVariable("index") int applicationIndex,
                                            @RequestParam(name = "edit", required = false) String edit,
                                            @PathVariable("applicantIndex") int candidateIndex,
                                            Model model) {
        applicationIndex = applicationIndex - 1;
        candidateIndex = candidateIndex - 1;

        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                indexValidator.validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
                        candidateIndex)) {
            Applicant applicant = agent.getApplications().get(applicationIndex).getApplicants().get(candidateIndex);
            model.addAttribute("candidate", applicant);

            CandidateRequestParameter parameter = new CandidateRequestParameter();
            if (edit != null)
                parameter.setEdit(true);
            model.addAttribute("param", parameter);
            return "candidate/index";
        } else
            return "error/wrong-input";
    }

    @PostMapping("/agent/application/{index}/applicant/{applicantIndex}")
    public String saveApplicantOfApplication(@ModelAttribute(name = "agent") Agent agent,
                                            @PathVariable("index") int applicationIndex,
                                            @RequestParam("save") String save,
                                            @RequestParam("name") String name,
                                            @RequestParam("email") String email,
                                            @PathVariable("applicantIndex") int candidateIndex,
                                            Model model) {
        applicationIndex = applicationIndex - 1;
        candidateIndex = candidateIndex - 1;

        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                indexValidator.validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
                        candidateIndex)) {
            Candidate candidate = agent.getApplications().get(applicationIndex).getApplicants().get(candidateIndex);
            candidate.setName(name);
            candidate.setEmail(email);
            agentService.updateCandidateInfo(candidate);
            model.addAttribute("candidate", candidate);
            return "redirect:/agent/application/" + (applicationIndex + 1) + "/applicant/" + (candidateIndex + 1);
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

        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex) &&
                indexValidator.validateApplicantsIndexes(agent.getApplications().get(applicationIndex).getApplicants(),
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

    @PostMapping("/agent/application/{index}/agentCollapse")
    @ResponseBody
    public void collapseApplication(HttpServletResponse response,
                                @ModelAttribute(name = "agent") Agent agent,
                                @PathVariable("index") int applicationIndex,
                                @RequestParam("collapsed") boolean collapsed) {
        applicationIndex = applicationIndex - 1;

        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
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
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
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

    @PostMapping("/agent/application/{index}/finalizeApplication")
    public String finalizeApplication(@ModelAttribute(name = "agent") Agent agent,
                                      @PathVariable("index") int applicationIndex) {
        applicationIndex = applicationIndex - 1;
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            if (agentService.finalizeApplication(agent.getApplications().get(applicationIndex))) {
                agent.getApplications().remove(applicationIndex);
                return "redirect:/agent";
            }
            else {
                return "redirect:/agent/application/" + (applicationIndex + 1) + "?finalizeError";
            }
        }
        else
            return "redirect:/error/wrong-input";
    }

    @GetMapping("/agent/application/{index}/possibleApplicants")
    public String getPossibleApplicants(@ModelAttribute("agent") Agent agent,
                                        @PathVariable("index") int applicationIndex,
                                        Model model) {
        applicationIndex = applicationIndex - 1;
        if (indexValidator.validateApplicationIndexes(agent.getApplications(), applicationIndex)) {
            Application application = agent.getApplications().get(applicationIndex);
            List<Applicant> possibleApplicants = agentService.getPossibleApplicants(application);

            model.addAttribute("possibleApplicants", possibleApplicants);
            model.addAttribute("app", application);
            model.addAttribute("applicationIndex", applicationIndex);
            return "/agent/application/index";
        }
        else
            return "/error/wrong-input";
    }
}
