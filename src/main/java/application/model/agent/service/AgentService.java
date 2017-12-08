package application.model.agent.service;

import application.model.agent.Agent;
import application.model.application.Application;
import application.model.enterprise.Enterprise;

public interface AgentService {
    Agent findAgentById(int agentId);
    void reorderApplicationsOfAgent(Agent agent);
    Enterprise findEnterpriseOfApplication(Application application);
    void updateApplicationInfo(Application application);
    void reorderApplicantsOfApplication(Application application);
}
