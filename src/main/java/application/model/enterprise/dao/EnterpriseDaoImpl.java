package application.model.enterprise.dao;

import application.model.enterprise.Enterprise;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.User;
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
        String sql = "SELECT user_id, name, email, contact_person_name FROM enterprises_info WHERE user_id=" + enterpriseId;
        List<Enterprise> enterprises = jdbcTemplate.query(sql, new EnterpriseMapper());
        return enterprises != null ? enterprises.get(0) : null;
    }

    @Override
    public void updateEnterpriseInfo(Enterprise enterprise) {
        String sql = "UPDATE enterprises_info SET name=?, email=? WHERE user_id=?";
        jdbcTemplate.update(sql, enterprise.getName(), enterprise.getEmail(), enterprise.getId());
    }

    @Override
    public void createNewEnterprise(User user, EnterpriseRegistrationForm form) {
        String sql = "INSERT INTO enterprises_info(user_id, name, email) VALUE (?,?,?)";
        jdbcTemplate.update(sql, user.getId(), form.getName(), form.getEmail());
    }

    private class EnterpriseMapper implements RowMapper<Enterprise> {
        @Override
        public Enterprise mapRow(ResultSet resultSet, int i) throws SQLException {
            Enterprise enterprise = new Enterprise();
            enterprise.setId(resultSet.getInt("user_id"));
            enterprise.setName(resultSet.getString("name"));
            enterprise.setEmail(resultSet.getString("email"));
            enterprise.setContactPersonName(resultSet.getString("contact_person_name"));
            return enterprise;
        }
    }
}
