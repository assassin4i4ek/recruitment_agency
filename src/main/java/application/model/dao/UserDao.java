package application.model.dao;

import application.model.user.User;

public interface UserDao {
    User findUserByUsername(String username);
}
