package application.model.dao;

import application.model.user.Login;
import application.model.user.User;

public interface UserDao {
    User validateUser(Login login);
}
