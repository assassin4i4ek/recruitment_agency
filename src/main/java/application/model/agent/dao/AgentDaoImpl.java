package application.model.agent.dao;

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
    public List<Integer> getAllAgentsSkillsInSphereSortedByLevel(String profession) {
        String sqlProfessionSphere = "SELECT sphere FROM professions_and_spheres WHERE profession='" + profession + "'";
        String sphere = jdbcTemplate.query(sqlProfessionSphere, (rs, i) -> rs.getString("sphere")).get(0);
        String sqlSkillsForSphere = "SELECT agent_id FROM agents_skills WHERE sphere='" + sphere + "' ORDER BY level DESC";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sqlSkillsForSphere);
        List<Integer> agentIds = new ArrayList<>(maps.size());
        for (Map<String, Object> map : maps) {
            agentIds.add((Integer) map.get("agent_id"));
        }

        return agentIds;
    }
}
