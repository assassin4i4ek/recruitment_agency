package model.dao;

import model.user_model.Login;
import model.user_model.User;
import org.springframework.context.annotation.Bean;

public interface UserDao {
    User validateUser(Login login);
}
