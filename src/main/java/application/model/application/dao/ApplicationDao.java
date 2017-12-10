package application.model.application.dao;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.user.User;

import java.util.List;

public interface ApplicationDao {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
    void updateApplication(Application application);
    void updateApplicationCollapsed(Application application);
    void updateApplicationCollapsedApplicants(Application application);
    void deleteApplication(Application application);
    void createNewApplication(User user, ApplicationRegistrationForm applicationRegistrationForm);
}
