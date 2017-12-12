package application.controllers;

import application.controllers.parameters.RegisterRequestParameter;
import application.model.application.ApplicationRegistrationForm;
import application.model.candidate.CandidateRegistrationForm;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"candidateRegistrationForm", "enterpriseRegistrationForm", "applicationRegistrationForm"})
public class WelcomeController {
    @Autowired
    private UserService userService;

    @ModelAttribute("candidateRegistrationForm")
    private CandidateRegistrationForm candidateRegistrationFrom() {
        return new CandidateRegistrationForm();
    }

    @ModelAttribute("enterpriseRegistrationForm")
    private EnterpriseRegistrationForm enterpriseRegistrationForm() {
        return new EnterpriseRegistrationForm();
    }

    @ModelAttribute("applicationRegistrationForm")
    private ApplicationRegistrationForm applicationRegistrationForm() {
        return new ApplicationRegistrationForm();
    }

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("param1", new RegisterRequestParameter());
        return "/index";
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        String role = authentication.getAuthorities().toString();
        String targetURL;
        if (role.contains("ROLE_AGENT"))
            targetURL = "/agent";
        else if (role.contains("ROLE_CANDIDATE"))
            targetURL = "/candidate";
        else if (role.contains("ROLE_ENTERPRISE"))
            targetURL = "/enterprise";
        else
            targetURL = "/error/access-denied";
        return "redirect:" + targetURL;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @GetMapping("/error/wrong-input")
    public String wrongInput() {
        return "/error/wrong-input";
    }

    @PostMapping("/registerCandidate")
    public String registerCandidate(@ModelAttribute("candidateRegistrationForm") CandidateRegistrationForm candidateRegistrationForm,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("confirmPassword") String confirmPassword,
                                    @RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("profession") String profession,
                                    @RequestParam("employmentType") String employmentType,
                                    @RequestParam("requiredSalaryCuPerMonth") String requiredSalaryCuPerMonth,
                                    @RequestParam("experience") String experience,
                                    @RequestParam("skills") String skills,
                                    Model model) {
        RegisterRequestParameter parameter = new RegisterRequestParameter();
        if (userService.validateUsername(candidateRegistrationForm)) {
            if (userService.validateEmail(candidateRegistrationForm)) {
                if (userService.validatePassword(candidateRegistrationForm)) {
                    if (profession.isEmpty() || userService.validateProfession(profession)) {
                        if (userService.validateSalary(requiredSalaryCuPerMonth)) {
                            userService.registerNewUser(candidateRegistrationForm);
                            candidateRegistrationForm.resetAll();
                            parameter.setSuccess(true);
                        }
                        else {
                            parameter.setSalaryError(true);
                        }
                    }
                    else {
                        parameter.setProfessionError(true);
                    }
                } else {
                    parameter.setPasswordError(true);
                }
            }
            else {
                parameter.setEmailError(true);
            }
        }
        else {
            parameter.setUsernameError(true);
        }
        model.addAttribute("param1", parameter);
        return "/index";
    }

    @PostMapping("/registerEnterprise")
    public String registerEnterprise(@ModelAttribute("enterpriseRegistrationForm") EnterpriseRegistrationForm enterpriseRegistrationForm,
                                     @ModelAttribute("applicationRegistrationForm") ApplicationRegistrationForm applicationRegistrationForm,
                                     @RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("confirmPassword") String confirmPassword,
                                     @RequestParam("name") String name,
                                     @RequestParam("email") String email,
                                     @RequestParam("contactPersonName") String contactPersonName,
                                     @RequestParam("profession") String profession,
                                     @RequestParam("quantity") String quantity,
                                     Model model) {
        RegisterRequestParameter parameter = new RegisterRequestParameter();
        if (userService.validateUsername(enterpriseRegistrationForm)) {
            if (userService.validateEmail(enterpriseRegistrationForm)) {
                if (userService.validatePassword(enterpriseRegistrationForm)) {
                    if (userService.validateApplicationProfession(applicationRegistrationForm)) {
                        if (applicationRegistrationForm.getProfession().isEmpty() ||
                                userService.validateApplicationQuantity(applicationRegistrationForm)) {
                                userService.registerNewUser(enterpriseRegistrationForm, applicationRegistrationForm);
                                enterpriseRegistrationForm.resetAll();
                                applicationRegistrationForm.resetAll();
                                parameter.setSuccess(true);
                        }
                        else {
                            parameter.setQuantityError(true);
                        }
                    }
                    else {
                        parameter.setProfessionError(true);
                    }
                } else {
                    parameter.setPasswordError(true);
                }
            }
            else {
                parameter.setEmailError(true);
            }
        }
        else {
            parameter.setUsernameError(true);
        }
        model.addAttribute("param1", parameter);
        return "/index";
    }
}
