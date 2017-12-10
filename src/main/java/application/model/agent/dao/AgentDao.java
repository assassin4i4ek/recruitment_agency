package application.model.agent.dao;

import java.util.List;

public interface AgentDao {
    List<Integer> getAllAgentsSkillsInSphereSortedByLevel(String profession);
}
