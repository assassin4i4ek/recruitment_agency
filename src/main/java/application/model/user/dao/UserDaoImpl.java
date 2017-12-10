package application.model.user.dao;

import application.model.candidate.CandidateRegistrationForm;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findUserByUsername(String username) {
        String sql = "SELECT id, username, password, enabled, role FROM users WHERE username='"
                + username + "'";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public void createNewUser(CandidateRegistrationForm form) {
        String sql = "INSERT INTO users(username, password, role) VALUE (?,?,?)";
        jdbcTemplate.update(sql, form.getUsername(), form.getPassword(), "ROLE_CANDIDATE");
    }

    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            Collection<GrantedAuthority> authorities = new ArrayList<>(1);
            authorities.add(new SimpleGrantedAuthority(resultSet.getString("role")));
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getShort("enabled") == 1,
                    true, true, true,
                    authorities,
                    resultSet.getInt("id"));
        }
    }
}
