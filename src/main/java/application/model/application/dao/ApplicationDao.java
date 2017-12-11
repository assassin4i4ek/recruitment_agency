package application.model.application.dao;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.user.User;

import java.util.List;
import java.util.Map;

public interface ApplicationDao {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
    void updateAgentApplication(Application application);
    void updateApplicationAgentCollapsed(Application application);
    void updateApplicationCollapsedApplicants(Application application);
    void deleteApplication(Application application);
    void createNewApplication(User user, ApplicationRegistrationForm form, int agentId);
    boolean validateProfession(String profession);
    Map<Integer, Long> listAgentIdsAndApplicationAmounts();
    List<Application> findApplicationsByEnterpriseId(int enterpriseId);
    void updateApplicationEnterpriseCollapsed(Application application);
    void reorderApplicationsOfEnterprise(List<Application> applications);
    void updateEnterpriseApplication(Application application);
}
