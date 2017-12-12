package application.model.application.service;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.enterprise.Enterprise;
import application.model.user.User;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
    void updateAgentApplicationInfo(Application application);
    void reorderApplicantsOfApplication(Application application);
    void updateApplicationAgentCollapsed(Application application);
    void updateApplicationCollapsedApplicants(Application application);
    boolean finalizeApplication(Application application);
    void registerNewApplication(User user, ApplicationRegistrationForm form);
    Map<Integer, Long> listAgentIdsAndApplicationAmounts();
    boolean validateQuantity(String quantity);
    boolean validateProfession(String profession);
    List<Application> findApplicationsByEnterpriseId(int enterpriseId);
    void reorderApplicationsOfEnterprise(List<Application> applications);
    void updateEnterpriseApplicationInfo(Application application);
    void updateApplicationEnterpriseCollapsed(Application application);
    boolean validateSalary(String requiredSalaryCuPerMonth);
}
