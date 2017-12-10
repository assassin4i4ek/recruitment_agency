package application.model.user.service;

import application.model.candidate.CandidateRegistrationForm;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.UserRegistrationForm;

public interface UserService {
    boolean validateUsername(UserRegistrationForm form);
    boolean validatePassword(UserRegistrationForm form);
    void registerNewUser(CandidateRegistrationForm form);
    boolean validateEmail(CandidateRegistrationForm form);
    boolean validateEmail(EnterpriseRegistrationForm form);
    void registerNewUser(EnterpriseRegistrationForm form);
}
