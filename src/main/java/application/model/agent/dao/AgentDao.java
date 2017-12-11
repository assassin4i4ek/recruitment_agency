package application.model.agent.dao;

import application.model.application.Application;

import java.util.List;

public interface AgentDao {
    List<Integer> getAllAgentsSkillsInSphereSortedByLevel(String profession);
    void increaseAgentsLevel(Application application);
    String getSphereOfProfession(String profession);
}
