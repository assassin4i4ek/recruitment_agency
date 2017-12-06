package application.model.application.dao;

import application.model.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ApplicationDaoImpl implements ApplicationDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Application> findApplicationsByAgentId(int agentId) {
        String sql = "SELECT id, enterprise_id, agent_id, registration_timestamp, profession, quantity, agent_note" +
                " FROM applications WHERE agent_id=" + agentId +
                " ORDER BY agent_order";
        return jdbcTemplate.query(sql, new ApplicationMapper());
    }

    @Override
    public void reorderApplicationsOfAgent(List<Application> applications) {
        int agentId = applications.get(0).getAgentId();
        for (int i = 0; i < applications.size(); ++i) {
            String sql = "UPDATE applications SET agent_order=" + i
                    + " WHERE agent_id=" + agentId;
            jdbcTemplate.update(sql);
        }
    }

    @Override
    public void updateApplication(Application application) {
        String sql = "UPDATE applications SET profession=?, quantity=?, agent_note=? WHERE id=?";
        jdbcTemplate.update(sql,
                application.getProfession(),
                application.getQuantity(),
                application.getAgentNote(),
                application.getId());
    }

    private class ApplicationMapper implements RowMapper<Application> {
        @Override
        public Application mapRow(ResultSet resultSet, int i) throws SQLException {
            Application application = new Application();
            application.setId(resultSet.getInt("id"));
            application.setEnterpriseId(resultSet.getInt("enterprise_id"));
            application.setAgentId(resultSet.getInt("agent_id"));
            application.setRegistrationTimestamp(resultSet.getTimestamp("registration_timestamp"));
            application.setProfession(resultSet.getString("profession"));
            application.setQuantity(resultSet.getShort("quantity"));
            BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                    resultSet.getBlob("agent_note").getBinaryStream()));

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
            return application;
        }
    }
}
