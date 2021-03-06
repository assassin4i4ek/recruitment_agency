package application.model.user.service;

import application.model.application.ApplicationRegistrationForm;
import application.model.candidate.CandidateRegistrationForm;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.UserRegistrationForm;

import java.util.List;

public interface UserService {
    boolean validateUsername(UserRegistrationForm form);
    boolean validatePassword(UserRegistrationForm form);
    void registerNewUser(CandidateRegistrationForm form);
    boolean validateEmail(CandidateRegistrationForm form);
    boolean validateEmail(EnterpriseRegistrationForm form);
    void registerNewUser(EnterpriseRegistrationForm form, ApplicationRegistrationForm applicationRegistrationForm);
    boolean validateApplicationProfession(ApplicationRegistrationForm applicationRegistrationForm);
    boolean validateApplicationQuantity(ApplicationRegistrationForm applicationRegistrationForm);
    boolean validateProfession(String profession);
    boolean validateSalary(String requiredSalaryCuPerMonth);
    List<String> getAvailableProfessionsList();
}
