package application.controllers;

import application.controllers.parameters.CandidateRequestParameter;
import application.model.candidate.Candidate;
import application.model.candidate.service.CandidateService;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"candidate","availableProfessions"})
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @ModelAttribute("candidate")
    private Candidate candidate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return candidateService.findCandidateById(user.getId());
    }

    @ModelAttribute("availableProfessions")
    private List<String> availableProfessions() {
        return candidateService.getAvailableProfessionsList();
    }

    @GetMapping("/candidate")
    public String getCandidate(@ModelAttribute("candidate") Candidate candidate,
                             @RequestParam(name = "edit", required = false) String edit,
                             Model model) {
        CandidateRequestParameter parameter = new CandidateRequestParameter();
        if (edit != null)
            parameter.setEdit(true);
        model.addAttribute("param", parameter);
        return "/candidate/index";
    }

    @PostMapping("/candidate")
    public String saveCandidate(@ModelAttribute("candidate") Candidate candidate,
                                 @RequestParam("save") String save,
                                 @RequestParam("name") String name,
                                 @RequestParam("email") String email) {
        candidate.setName(name);
        candidate.setEmail(email);
        candidateService.updateCandidateInfo(candidate);
        return "redirect:/candidate";
    }
}
