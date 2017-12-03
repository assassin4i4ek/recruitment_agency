package application.model.dao;

import application.model.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 03.12.2017.
 */
public class ApplicationDaoImpl implements ApplicationDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Application> findApplicationsByAgentId(int agentId) {
        String sql = "SELECT id, enterprise_id, agent_id registration_date, profession, quantity, agent_note" +
                " FROM applications WHERE agent_id=" + agentId;
        List<Application> applications = jdbcTemplate.query(sql, new ApplicationMapper());
        return applications;
    }

    private class ApplicationMapper implements RowMapper<Application> {
        @Override
        public Application mapRow(ResultSet resultSet, int i) throws SQLException {
            return null;
        }
    }
}
