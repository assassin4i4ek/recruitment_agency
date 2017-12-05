package application.model.enterprise.dao;

import application.model.enterprise.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnterpriseDaoImpl implements EnterpriseDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Enterprise findEnterpriseById(int enterpriseId) {
        String sql = "SELECT user_id, name FROM enterprises_info WHERE user_id=" + enterpriseId;
        List<Enterprise> enterprises = jdbcTemplate.query(sql, new EnterpriseMapper());
        return enterprises != null ? enterprises.get(0) : null;
    }

    private class EnterpriseMapper implements RowMapper<Enterprise> {
        @Override
        public Enterprise mapRow(ResultSet resultSet, int i) throws SQLException {
            Enterprise enterprise = new Enterprise();
            enterprise.setId(resultSet.getInt("user_id"));
            enterprise.setName(resultSet.getString("name"));
            return enterprise;
        }
    }
}
