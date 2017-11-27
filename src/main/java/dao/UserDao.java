package dao;

import model.Login;
import model.User;

public interface UserDao {
    User validateUser(Login login);
}
