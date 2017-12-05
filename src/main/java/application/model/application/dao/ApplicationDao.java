package application.model.application.dao;

import application.model.application.Application;

import java.util.List;

public interface ApplicationDao {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(List<Application> applications);
}
