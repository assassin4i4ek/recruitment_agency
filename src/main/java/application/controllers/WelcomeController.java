package application.controllers;

import application.controllers.parameters.RegisterRequestParameter;
import application.model.candidate.CandidateRegistrationForm;
import application.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("candidateRegistrationForm")
public class WelcomeController {
    @Autowired
    private UserService userService;

    @ModelAttribute("candidateRegistrationForm")
    private CandidateRegistrationForm candidateRegistrationFrom() {
        return new CandidateRegistrationForm();
    }

    @GetMapping("/")
    public String root(Model model) {
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
                                    Model model) {
        RegisterRequestParameter parameter = new RegisterRequestParameter();
        if (userService.validateUsername(candidateRegistrationForm)) {
            if (userService.validateEmail(candidateRegistrationForm)) {
                if (userService.validatePassword(candidateRegistrationForm)) {
                    userService.registerNewUser(candidateRegistrationForm);
                    candidateRegistrationForm.resetAll();
                    parameter.setSuccess(true);
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
