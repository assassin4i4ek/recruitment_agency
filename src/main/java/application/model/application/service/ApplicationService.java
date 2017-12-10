package application.model.application.service;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.user.User;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
    void updateApplicationInfo(Application application);
    void reorderApplicantsOfApplication(Application application);
    void updateApplicationCollapsed(Application application);
    void updateApplicationCollapsedApplicants(Application application);
    boolean finalizeApplication(Application application);
    void registerNewApplication(User user, ApplicationRegistrationForm form);
    boolean validateProfession(ApplicationRegistrationForm applicationRegistrationForm);
    Map<Integer, Long> listAgentIdsAndApplicationAmounts();
    boolean validateQuantity(ApplicationRegistrationForm applicationRegistrationForm);
}
