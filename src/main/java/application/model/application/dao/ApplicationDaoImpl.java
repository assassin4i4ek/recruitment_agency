package application.model.application.dao;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.application.EmploymentType;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ApplicationDaoImpl implements ApplicationDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Application> findApplicationsByAgentId(int agentId) {
        String sql = "SELECT id, enterprise_id, agent_id, registration_timestamp, profession, quantity, employment_type," +
                " salary_cu_per_month, demanded_skills, agent_note, agent_collapsed, agent_collapsed_applicants" +
                " FROM applications WHERE agent_id=" + agentId +
                " ORDER BY agent_order";
        return jdbcTemplate.query(sql, new AgentApplicationMapper());
    }

    @Override
    public void reorderApplicationsOfAgent(List<Application> applications) {
        for (int i = 0; i < applications.size(); ++i) {
            String sql = "UPDATE applications SET agent_order=" + i
                    + " WHERE id=" + applications.get(i).getId();
            jdbcTemplate.update(sql);
        }
    }

    @Override
    public void updateAgentApplication(Application application) {
        String sql = "UPDATE applications SET profession=?, quantity=?, employment_type=?, salary_cu_per_month=?, demanded_skills=?, agent_note=? WHERE id=?";
        jdbcTemplate.update(sql,
                application.getProfession(),
                application.getQuantity(),
                application.getEmploymentType().name(),
                application.getSalaryCuPerMonth(),
                application.getDemandedSkills(),
                application.getAgentNote(),
                application.getId());
    }

    @Override
    public void updateApplicationAgentCollapsed(Application application) {
        String sql = "UPDATE applications SET agent_collapsed=? WHERE id=?";
        jdbcTemplate.update(sql, application.isAgentCollapsed(), application.getId());
    }

    @Override
    public void updateApplicationCollapsedApplicants(Application application) {
        String sql = "UPDATE applications SET agent_collapsed_applicants=? WHERE id=?";
        jdbcTemplate.update(sql, application.isAgentCollapsedApplicants(), application.getId());
    }

    @Override
    public void deleteApplication(Application application) {
        String sql = "DELETE FROM applications WHERE id=" + application.getId();
        jdbcTemplate.update(sql);
    }

    @Override
    public void createNewApplication(User user, ApplicationRegistrationForm form, int agentId) {
        String sql = "INSERT INTO applications(agent_id, enterprise_id, profession, quantity, employment_type," +
                " salary_cu_per_month, demanded_skills) VALUE (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, agentId, user.getId(), form.getProfession(), form.getQuantity(), form.getEmploymentType().name(),
                form.getSalaryCuPerMonth(), form.getDemandedSkills());
    }

    @Override
    public boolean validateProfession(String profession) {
        String sql = "SELECT profession FROM professions_and_spheres WHERE profession='" + profession + "'";
        List<String> result = jdbcTemplate.query(sql, (rs, i) -> rs.getString("profession"));
        return result != null && result.size() > 0;
    }

    @Override
    public Map<Integer, Long> listAgentIdsAndApplicationAmounts() {
        String sql = "SELECT agents_info.user_id AS agent_id, COUNT(applications.id) AS amount FROM agents_info" +
                " LEFT JOIN applications ON applications.agent_id = agents_info.user_id" +
                " GROUP BY agents_info.user_id";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        Map<Integer, Long> amountsMap = new HashMap<>(maps.size());
        for (Map<String, Object> map : maps) {
            Integer agentId = (Integer) map.get("agent_id");
            Long amount = (Long) map.get("amount");
            amountsMap.put(agentId, amount);
        }

        return amountsMap;
    }

    @Override
    public List<Application> findApplicationsByEnterpriseId(int enterpriseId) {
        String sql = "SELECT id, registration_timestamp, profession, quantity, employment_type, salary_cu_per_month," +
                " demanded_skills, enterprise_collapsed" +
                " FROM applications WHERE enterprise_id=" + enterpriseId + " ORDER BY enterprise_order";
        return jdbcTemplate.query(sql, (rs, i) -> {
            Application application = new Application();
            application.setId(rs.getInt("id"));
            application.setEnterpriseId(enterpriseId);
            application.setRegistrationTimestamp(rs.getTimestamp("registration_timestamp"));
            application.setProfession(rs.getString("profession"));
            application.setQuantity(rs.getShort("quantity"));
            application.setEmploymentType(EmploymentType.valueOf(rs.getString("employment_type")));
            application.setSalaryCuPerMonth(rs.getInt("salary_cu_per_month"));
            application.setEnterpriseCollapsed(rs.getBoolean("enterprise_collapsed"));
            Blob demandedSkillsBlob = rs.getBlob("demanded_skills");
            if (demandedSkillsBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        demandedSkillsBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                application.setDemandedSkills(stringBuilder.toString());
            }
            return application;
        });
    }

    @Override
    public void updateApplicationEnterpriseCollapsed(Application application) {
        String sql = "UPDATE applications SET enterprise_collapsed=? WHERE id=?";
        jdbcTemplate.update(sql, application.isEnterpriseCollapsed(), application.getId());
    }

    @Override
    public void reorderApplicationsOfEnterprise(List<Application> applications) {
        for (int i = 0; i < applications.size(); ++i) {
            String sql = "UPDATE applications SET enterprise_order=" + i
                    + " WHERE id=" + applications.get(i).getId();
            jdbcTemplate.update(sql);
        }
    }

    @Override
    public void updateEnterpriseApplication(Application application) {
        String sql = "UPDATE applications SET profession=?, quantity=?, employment_type=?, salary_cu_per_month=?," +
                " demanded_skills=? WHERE id=?";
        jdbcTemplate.update(sql,
                application.getProfession(), application.getQuantity(), application.getEmploymentType().name(),
                application.getSalaryCuPerMonth(), application.getDemandedSkills(), application.getId());
    }

    @Override
    public List<String> getAvailableProfessionsList() {
        String sql = "SELECT profession FROM professions_and_spheres INNER JOIN spheres" +
                " ON professions_and_spheres.sphere = spheres.name ORDER BY rank";
        return jdbcTemplate.query(sql, (rs,i) -> rs.getString("profession"));
    }

    private class AgentApplicationMapper implements RowMapper<Application> {
        @Override
        public Application mapRow(ResultSet resultSet, int i) throws SQLException {
            Application application = new Application();
            application.setId(resultSet.getInt("id"));
            application.setEnterpriseId(resultSet.getInt("enterprise_id"));
            application.setAgentId(resultSet.getInt("agent_id"));
            application.setRegistrationTimestamp(resultSet.getTimestamp("registration_timestamp"));
            application.setProfession(resultSet.getString("profession"));
            application.setQuantity(resultSet.getShort("quantity"));
            application.setEmploymentType(EmploymentType.valueOf(resultSet.getString("employment_type")));
            application.setSalaryCuPerMonth(resultSet.getInt("salary_cu_per_month"));

            Blob demandedSkillsBlob = resultSet.getBlob("demanded_skills");
            if (demandedSkillsBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        demandedSkillsBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                application.setDemandedSkills(stringBuilder.toString());
            }

            Blob agentNoteBlob = resultSet.getBlob("agent_note");
            if (agentNoteBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        agentNoteBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                application.setAgentNote(stringBuilder.toString());
            }
            application.setAgentCollapsed(resultSet.getBoolean("agent_collapsed"));
            application.setAgentCollapsedApplicants(resultSet.getBoolean("agent_collapsed_applicants"));
            return application;
        }
    }
}
