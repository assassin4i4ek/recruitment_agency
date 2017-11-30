package model.dao;

import model.user_model.Login;
import model.user_model.User;
import model.user_model.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User validateUser(Login login) {
        String sql = "SELECT * FROM users WHERE username='" + login.getUsername() +
                "' AND password='" + login.getPassword() + "'";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        return users.size() > 0 ? users.get(0) : null;
    }

    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setType(UserType.valueOf(resultSet.getString("user_type")));
            return user;
        }
    }
}
