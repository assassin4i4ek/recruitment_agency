package application.model.user.service;

import application.model.candidate.CandidateRegistrationForm;

public interface UserService {
    boolean validateUsername(CandidateRegistrationForm form);
    boolean validatePassword(CandidateRegistrationForm form);
    void registerNewUser(CandidateRegistrationForm form);
    boolean validateEmail(CandidateRegistrationForm candidateRegistrationForm);
}
