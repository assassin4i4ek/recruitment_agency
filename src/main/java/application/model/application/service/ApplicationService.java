package application.model.application.service;

import application.model.application.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
    void updateApplicationInfo(Application application);
    void reorderApplicantsOfApplication(Application application);
    void updateApplicationCollapsed(Application application);
    void updateApplicationCollapsedApplicants(Application application);
}
