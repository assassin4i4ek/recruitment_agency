package application.model.user.dao;

import application.model.candidate.CandidateRegistrationForm;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.User;

public interface UserDao {
    User findUserByUsername(String username);
    void createNewUser(CandidateRegistrationForm form);
    void createNewUser(EnterpriseRegistrationForm form);
}
