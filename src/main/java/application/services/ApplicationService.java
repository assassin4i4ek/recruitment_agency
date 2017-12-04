package application.services;

import application.model.application.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
}
