package application.model.dao;

import application.model.application.Application;

import java.util.List;

public interface ApplicationDao {
    List<Application> findApplicationsByAgentId(int agentId);
    void reorderApplicationsOfAgent(int agentId);
}
