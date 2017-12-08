package application.controllers;

import application.model.candidate.Candidate;
import application.model.candidate.service.CandidateService;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @ModelAttribute("candidate")
    private Candidate candidate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return candidateService.findCandidateById(user.getId());
    }

    @GetMapping("/candidate")
    public String candidate() {
        return "/candidate/index";
    }
}
