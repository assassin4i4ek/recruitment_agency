package application.model.agent.dao;

import application.model.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AgentDaoImpl implements AgentDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public String getSphereOfProfession(String profession) {
        String sql = "SELECT sphere FROM professions_and_spheres WHERE profession='" + profession + "'";
        return jdbcTemplate.query(sql, (rs, i) -> rs.getString("sphere")).get(0);
    }

    @Override
    public List<Integer> getAllAgentsSkillsInSphereSortedByLevel(String profession) {
        String sphere = getSphereOfProfession(profession);
        String sqlSkillsForSphere = "SELECT agent_id FROM agents_skills WHERE sphere='" + sphere + "' ORDER BY level DESC";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sqlSkillsForSphere);
        List<Integer> agentIds = new ArrayList<>(maps.size());
        for (Map<String, Object> map : maps) {
            agentIds.add((Integer) map.get("agent_id"));
        }

        return agentIds;
    }

    @Override
    public void increaseAgentsLevel(Application application) {
        String sphere = getSphereOfProfession(application.getProfession());
        String sql = "UPDATE agents_skills SET level = level + 1 WHERE agent_id=" + application.getAgentId() +
                " AND sphere='" + sphere + "'";
        jdbcTemplate.update(sql);
    }

}
